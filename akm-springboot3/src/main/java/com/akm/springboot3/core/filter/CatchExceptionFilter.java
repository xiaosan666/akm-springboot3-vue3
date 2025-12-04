package com.akm.springboot3.core.filter;

import cn.hutool.core.util.IdUtil;
import com.akm.springboot3.core.constant.AkmConstants;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.utils.StringUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 捕获filter异常，该filter优先级必须为最高，可以捕获所有过滤器的异常
 * 参考：<a href="https://blog.csdn.net/flyer5/article/details/103836207">...</a>
 *
 * @author xiaojun
 *
 */
public class CatchExceptionFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(CatchExceptionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 设置请求唯一id，优先使用请求头中的随机数，请求头没有则重新生成
        String reqId = request.getHeader(AkmConstants.HTTP_HEADER_REQ_ID);
        if (StringUtils.isBlank(reqId)) {
            reqId = IdUtil.simpleUUID();
        }
        request.setAttribute(AkmConstants.HTTP_HEADER_REQ_ID, reqId);
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (BusinessException e) {
            log.error("filter.BusinessException --------ReqId:{},RequestURI:{}", reqId, request.getServletPath());
            request.setAttribute("filter.BusinessException", e);
            request.getRequestDispatcher("/error/filter/BusinessException").forward(servletRequest, servletResponse);
        } catch (Exception e) {
            log.error("filter.Exception --------ReqId:{},RequestURI:{}", reqId, request.getServletPath(), e);
        }
    }

    @Override
    public void destroy() {
    }
}
