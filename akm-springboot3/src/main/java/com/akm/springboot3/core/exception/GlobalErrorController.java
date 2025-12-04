package com.akm.springboot3.core.exception;

import com.akm.springboot3.core.domain.RestResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@ResponseBody
@Slf4j
public class GlobalErrorController implements ErrorController {

    @RequestMapping("/error")
    public RestResult<?> handleNotFoundError(HttpServletResponse response) {
        log.warn("msg：{}", "没有访问权限或请求地址不存在！");
        response.setStatus(404);
        return RestResult.fail(CodeMsg.NOT_FOUND);
    }

    /**
     * 处理在过滤器中发生的BusinessException异常
     */
    @RequestMapping(value = "/error/filter/BusinessException", method = {RequestMethod.POST, RequestMethod.GET})
    public void filterBusinessException(HttpServletRequest request) {
        throw (BusinessException) request.getAttribute("filter.BusinessException");
    }

    @RequestMapping("/.well-known/**")
    public ResponseEntity<Void> handleUnknownWellKnown() {
        // 返回 404，或改为返回 204 无内容
        return ResponseEntity.notFound().build();
    }
}
