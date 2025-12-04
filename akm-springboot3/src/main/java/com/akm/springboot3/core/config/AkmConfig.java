package com.akm.springboot3.core.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义的配置参数，在application.yml中自定义的配置可以映射的此类
 *
 * @author xiaojun
 *
 */
@Component
@ConfigurationProperties(prefix = "akm")
@Getter
@Setter
@ToString
public class AkmConfig {

    private String cacheType;

    private Boolean enableLoginCaptcha;

    private Boolean forceGenerateRsaKey;

    private Boolean forcePasswordChange;

    private Integer passwordEffectiveDays;

    private Integer tokenTimeout;

    private Integer accountEffectiveDays;

    private Boolean enabledCorsAllow;
    private List<String> corsAllowDomain;

    private List<String> alwaysExcludeUrls;

    private Boolean enabledEncrypt;
    private List<String> encryptExcludeUrls;

    private Boolean enabledSign;
    private Integer signTimeTolerance;
    private List<String> signExcludeUrls;

    private Boolean enabledAuth;
    private List<String> openUrls;
    private List<String> publicUrls;

    private Boolean enabledApiNameCheck;
    private List<String> apiNameCheckExcludeUrls;

    private String sensitiveWord;
    private List<String> sensitiveWordExcludeUrls;

    private Boolean enabledApiFreq;
    private String apiFreq;
    private String opApiFreq;
    private List<String> freqExcludeUrls;

    private Login4a login4a;

    private Mybatis mybatis;

    @Getter
    @Setter
    @ToString
    public static class Login4a {
        private Boolean enable;
        private String akm4aServerUrl;
        private String aesKey;
        private String successRedirectUrl;
    }

    @Getter
    @Setter
    @ToString
    public static class Mybatis {
        private Boolean printSql;
    }
}
