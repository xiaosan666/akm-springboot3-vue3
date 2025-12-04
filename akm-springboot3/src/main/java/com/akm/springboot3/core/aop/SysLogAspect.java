package com.akm.springboot3.core.aop;

import cn.hutool.json.JSONUtil;
import com.akm.springboot3.core.annotation.IgnoreSysLog;
import com.akm.springboot3.core.constant.AkmConstants;
import com.akm.springboot3.core.constant.NumberConstant;
import com.akm.springboot3.core.domain.RestResult;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.utils.IpUtils;
import com.akm.springboot3.core.utils.SnowflakeUtils;
import com.akm.springboot3.core.utils.UserThreadUtils;
import com.akm.springboot3.web.sys.domain.LoginWrapper;
import com.akm.springboot3.web.sys.entity.SysLog;
import com.akm.springboot3.web.sys.entity.SysLogEx;
import com.akm.springboot3.web.sys.entity.SysLogLogin;
import com.akm.springboot3.web.sys.entity.SysUser;
import com.akm.springboot3.web.sys.mapper.SysUserMapper;
import com.akm.springboot3.web.sys.service.SysLogExceptionService;
import com.akm.springboot3.web.sys.service.SysLogLoginService;
import com.akm.springboot3.web.sys.service.SysLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * 异步记录接口访问日志（记录请求信息、响应结果，异常信息）
 * 注：当请求不能到达接口就失败则不会记录，如请
 * 求404，请求参数不符合接口要求等情况
 *
 * @author xiaojun
 *
 */
@Aspect
@Component
@Order(1)
public class SysLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);
    private static final String LOGIN_API_URI = "/auth/open/login";
    private final SysLogService sysLogService;
    private final SysLogExceptionService sysLogExceptionService;
    private final SysLogLoginService sysLogLoginService;
    private final SysUserMapper sysUserMapper;

    /**
     * todo
     * 由于日志表增长太快，导致无法正常排查异常
     * 这里设置记录日志最大长度，当请求是success，日志长度超过此设置，则不记录请求结果
     */
    private static final int LOG_MAX_LENGTH = 20000;

    SysLogAspect(SysLogService sysLogService,
                 SysLogExceptionService sysLogExceptionService,
                 SysLogLoginService sysLogLoginService,
                 SysUserMapper sysUserMapper) {
        this.sysLogService = sysLogService;
        this.sysLogExceptionService = sysLogExceptionService;
        this.sysLogLoginService = sysLogLoginService;
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * 异常内容转为字符串
     * BusinessException异常忽略堆栈信息
     */
    private static String printStackTraceToString(Throwable t) {
        if (t instanceof BusinessException) {
            return t.toString();
        }
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }

    /**
     * 以 api 包下定义的所有请求为切入点
     */
    @Around("execution(* com.akm.springboot3.*.*.api..*.*(..))||execution(* com.akm.springboot3.*.*.*.api..*.*(..))")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录请求日志
        SysLog record = new SysLog();
        record.setStartTime(System.currentTimeMillis());
        // 获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return joinPoint.proceed();
        }
        HttpServletRequest request = attributes.getRequest();
        try {
            // 本次请求参数
            Object[] args = joinPoint.getArgs();

            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();

            // 忽略判断是否存在忽略日志的注解
            if (method.isAnnotationPresent(IgnoreSysLog.class)) {
                return joinPoint.proceed();
            }
            // 记录请求接口描述
            if (method.isAnnotationPresent(Operation.class)) {
                Operation log = method.getAnnotation(Operation.class);
                // 事件类型(操作描述)
                record.setApiDescription(log.summary());
            }
            // 保存请求参数
            List<Object> argList = new ArrayList<>();
            Parameter[] parameters = method.getParameters();
            // 根据接口RequestBody和RequestParam注解获取接口参数
            for (int i = 0; i < parameters.length; i++) {
                Object arg = args[i];
                // 将RequestBody注解修饰的参数作为请求参数
                RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
                if (requestBody != null) {
                    argList.add(arg);
                }
            }
            // 记录请求参数
            record.setParameter(argList.size() == 1 ? new ObjectMapper().writeValueAsString(argList.get(0)) : new ObjectMapper().writeValueAsString(argList));
            // 执行请求
            Object result = joinPoint.proceed(args);
            String resultStr;
            // 接口返回RestResult，只取data
            if (result instanceof RestResult) {
                Object data = ((RestResult<?>) result).getData();
                resultStr = data instanceof String ? String.valueOf(data) : new ObjectMapper().writeValueAsString(data == null ? "" : data);
            } else if (result instanceof String || result instanceof Integer || result instanceof Long || result instanceof Boolean || result instanceof Double) {
                resultStr = String.valueOf(result);
            } else {
                resultStr = new ObjectMapper().writeValueAsString(result);
            }
            if (resultStr != null && resultStr.length() > LOG_MAX_LENGTH) {
                resultStr = null;
            }
            record.setResult(resultStr);
            saveLog(request, record, true, null);
            return result;
        } catch (BusinessException e) {
            // 业务异常
            record.setResult(e.getMessage());
            saveLog(request, record, false, e);
            throw new BusinessException(e.getCode(), e.getMessage(), e);
        } catch (Exception e) {
            // 其他异常
            record.setResult(e.toString());
            saveLog(request, record, false, e);
            throw e;
        }
    }

    private void saveLog(HttpServletRequest request, SysLog record, boolean success, Exception ex) {
        try {
            record.setSpendTime(System.currentTimeMillis() - record.getStartTime());
            record.setSuccess(NumberConstant.ONE);
            String logId = SnowflakeUtils.id();
            record.setId(logId);
            record.setUri(request.getServletPath());
            record.setMethod(request.getMethod());
            record.setIp(IpUtils.getClientIpAddress(request));
            record.setUrl(request.getRequestURL().toString());
            record.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
            String reqId = (String) request.getAttribute(AkmConstants.HTTP_HEADER_REQ_ID);
            record.setRequestId(reqId);
            record.setUserId(UserThreadUtils.getUserId());
            record.setUsername(UserThreadUtils.getUserName());
            record.setName(UserThreadUtils.getName());
            Object clientType = UserThreadUtils.getClientType();
            if (clientType != null) {
                record.setClientType(UserThreadUtils.getClientType());
            }
            // 记录请求异常日志
            if (!success) {
                SysLogEx exceptionRecord = new SysLogEx();
                exceptionRecord.setLogId(logId);
                // 目前只区分1.BusinessException；7.其他Exception
                exceptionRecord.setType((byte) (ex instanceof BusinessException ? 1 : 7));
                exceptionRecord.setContent(printStackTraceToString(ex));
                sysLogExceptionService.asyncInsert(exceptionRecord);
            }
            // 记录登录日志
            if (LOGIN_API_URI.equalsIgnoreCase(request.getServletPath())) {
                LoginWrapper loginWrapper = JSONUtil.toBean(record.getParameter(), LoginWrapper.class);
                record.setUsername(loginWrapper.getUsername());
                record.setClientType(loginWrapper.getClientType());
                SysLogLogin sysLogLogin = new SysLogLogin();
                sysLogLogin.setLogId(logId);
                if (success) {
                    SysUser user = sysUserMapper.selectOneByUsername(loginWrapper.getUsername());
                    if (user != null) {
                        record.setUserId(user.getId());
                        record.setName(user.getRealname());
                    }
                }
                sysLogLoginService.asyncInsert(sysLogLogin);
            }
            sysLogService.asyncInsert(record);
        } catch (Exception e) {
            logger.error("记录系统访问日志时发生异常", e);
        }
    }
}
