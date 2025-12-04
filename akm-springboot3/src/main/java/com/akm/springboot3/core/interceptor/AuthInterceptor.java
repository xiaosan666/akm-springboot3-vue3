package com.akm.springboot3.core.interceptor;

import cn.hutool.core.collection.CollUtil;
import com.akm.springboot3.core.config.AkmConfig;
import com.akm.springboot3.core.constant.AkmConstants;
import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.enums.ClientTypeEnum;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.exception.CodeMsg;
import com.akm.springboot3.core.utils.*;
import com.akm.springboot3.web.sys.domain.CacheUser;
import com.akm.springboot3.web.sys.domain.SysRoleBaseInfo;
import com.akm.springboot3.web.sys.service.SysApiService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

/**
 * 权限校验
 *
 * @author xiaojun
 *
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    private final AkmConfig akmConfig;
    private final SysApiService sysApiService;

    /**
     * 白名单
     */
    private final List<String> excludeUris;

    AuthInterceptor(AkmConfig akmConfig, SysApiService sysApiService) {
        this.akmConfig = akmConfig;
        this.sysApiService = sysApiService;
        this.excludeUris = ArrayUtil.merge(akmConfig.getOpenUrls(), akmConfig.getAlwaysExcludeUrls());
    }



    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        String requestUri = request.getServletPath();
        String token = request.getHeader(AkmConstants.HTTP_HEADER_TOKEN);
        CacheUser userInfo = null;
        if (StringUtils.isNotBlank(token)) {
            userInfo = CacheUtils.get(RedisKeys.TOKEN.concat(token));
            if (userInfo != null) {
                // web端更新缓存时间，缓存token与用户信息
                String clientType = userInfo.getClientType();
                if (String.valueOf(ClientTypeEnum.WEB.getValue()).equals(clientType)) {
                    CacheUtils.expire(RedisKeys.TOKEN.concat(token), akmConfig.getTokenTimeout() * 60L);
                }
                // 在请求线程中存入用户基本信息，供业务使用
                UserThreadUtils.setUserInfo(userInfo);
            }
        }
        // 不启用或为open Api不用校验权限
        if (Boolean.FALSE.equals(akmConfig.getEnabledAuth()) || isOpenApi(requestUri)) {
            return true;
        }
        AssertUtils.notBlank(token, CodeMsg.UNAUTHORIZED);
        if (userInfo == null) {
            throw new BusinessException(CodeMsg.UNAUTHORIZED);
        }
        // 校验是否public Api
        if (isPublicApi(requestUri)) {
            return true;
        }
        // 校验是否有api权限
        List<String> roleIds = CollUtil.map(userInfo.getRoleList(), SysRoleBaseInfo::getId, true);
        AssertUtils.notEmpty(roleIds, CodeMsg.FORBIDDEN);
        AssertUtils.isTrue(isAccessApi(requestUri, roleIds), CodeMsg.FORBIDDEN);
        return true;
    }

    @Override
    public void afterCompletion(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, @Nullable Exception ex) {
        UserThreadUtils.clear();
    }

    /**
     * 判断requestUri为open url
     * 若包含则不需要权限即可访问
     */
    private boolean isOpenApi(String requestUri) {
        if (requestUri.contains(AkmConstants.OPEN_API)) {
            return true;
        }
        return StringUtils.pathMatch(this.excludeUris, requestUri);
    }

    /**
     * 判断requestUri是否为public url
     * 若包含则只需要登陆即可访问
     */
    private boolean isPublicApi(String requestUri) {
        if (requestUri.contains(AkmConstants.PUBLIC_API)) {
            return true;
        }
        List<String> publicUrls = akmConfig.getPublicUrls();
        return StringUtils.pathMatch(publicUrls, requestUri);
    }

    /**
     * 判断requestUri是否有权限访问
     */
    private boolean isAccessApi(String requestUri, List<String> roleIds) {
        for (String roleId : roleIds) {
            // 根据roleId查询对应的api，判断该请求是否有权限
            List<String> uriList = sysApiService.getAndCacheUriByRoleId(roleId);
            // 验证是否有任何匹配的url
            if (StringUtils.pathMatch(uriList, requestUri)) {
                return true;
            }
        }
        // 没有权限，则再次尝试从数据库获取
        for (String roleId : roleIds) {
            List<String> uriList = sysApiService.getUriByRoleId(roleId);
            if (StringUtils.pathMatch(uriList, requestUri)) {
                log.warn("从缓存获取权限失败，从数据库获取成功，requestUri：{}，roleId：{}，role uriList：{}", requestUri, roleId, uriList);
                CacheUtils.set(RedisKeys.ROLE_URI.concat(roleId), uriList);
                return true;
            }
        }
        log.warn("msg：{}，requestUri：{}，roleIds：{}", CodeMsg.FORBIDDEN.getMsg(), requestUri, roleIds);
        return false;
    }
}
