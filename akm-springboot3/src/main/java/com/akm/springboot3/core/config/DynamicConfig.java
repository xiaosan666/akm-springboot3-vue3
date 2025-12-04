package com.akm.springboot3.core.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 动态配置示例
 * 使用 @RefreshScope 注解支持配置动态刷新
 * 
 * 使用方法：
 * 1. 在 Config Server 中修改配置
 * 2. 发送 POST 请求到 /actuator/refresh
 * 3. 此类中的配置会自动更新
 *
 * @author akm
 */
@Slf4j
@Getter
@Component
@RefreshScope  // 关键注解：标记此 Bean 支持配置刷新
public class DynamicConfig {

    @Value("${akm.cacheType:local}")
    private String cacheType;

    @Value("${akm.enabledAuth:true}")
    private Boolean enabledAuth;

    @Value("${akm.enableLoginCaptcha:false}")
    private Boolean enableLoginCaptcha;

    @Value("${akm.forcePasswordChange:false}")
    private Boolean forcePasswordChange;

    @Value("${akm.enabledCorsAllow:false}")
    private Boolean enabledCorsAllow;

    @Value("${akm.enabledEncrypt:false}")
    private Boolean enabledEncrypt;

    @Value("${akm.enabledSign:false}")
    private Boolean enabledSign;

    @Value("${akm.enabledApiFreq:true}")
    private Boolean enabledApiFreq;

    /**
     * 记录配置刷新事件
     * 当配置刷新时，Spring 会销毁并重新创建此 Bean
     */
    public void logRefresh() {
        log.info("========================================");
        log.info("配置已刷新:");
        log.info("缓存类型: {}", cacheType);
        log.info("启用权限校验: {}", enabledAuth);
        log.info("启用登录验证码: {}", enableLoginCaptcha);
        log.info("强制修改密码: {}", forcePasswordChange);
        log.info("启用跨域: {}", enabledCorsAllow);
        log.info("启用加解密: {}", enabledEncrypt);
        log.info("启用签名校验: {}", enabledSign);
        log.info("启用接口频率限制: {}", enabledApiFreq);
        log.info("========================================");
    }
}

