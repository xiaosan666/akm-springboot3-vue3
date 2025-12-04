package com.akm.springboot3.core.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Spring Cloud Config 配置健康检查
 * 用于验证是否成功从配置中心获取配置
 *
 * @author akm
 */
@Slf4j
@Configuration
@RefreshScope  // 支持配置动态刷新
@ConditionalOnProperty(name = "spring.cloud.config.enabled", havingValue = "true", matchIfMissing = false)
public class ConfigServerHealthCheck {

    private final ConfigurableEnvironment environment;

    @Value("${spring.cloud.config.uri:未配置}")
    private String configServerUri;

    @Value("${spring.application.name:未配置}")
    private String applicationName;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    public ConfigServerHealthCheck(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void checkConfigSource() {
        log.info("========================================");
        log.info("Spring Cloud Config 配置信息:");
        log.info("Config Server URI: {}", configServerUri);
        log.info("Application Name: {}", applicationName);
        log.info("Active Profile: {}", activeProfile);
        
        // 检查配置来源
        String configSource = environment.getProperty("spring.config.name");
        log.info("Config Source: {}", configSource);
        
        // 验证是否从 Config Server 获取配置
        String[] propertySources = environment.getPropertySources().stream()
            .map(ps -> ps.getName())
            .toArray(String[]::new);
        
        boolean fromConfigServer = false;
        for (String source : propertySources) {
            if (source.contains("configClient") || source.contains("Config resource")) {
                log.info("✅ 成功从 Config Server 获取配置: {}", source);
                fromConfigServer = true;
            }
        }
        
        if (!fromConfigServer) {
            log.warn("⚠️  未从 Config Server 获取配置，使用本地配置文件");
        }
        
        // 打印一些关键配置验证
        String cacheType = environment.getProperty("akm.cacheType");
        String datasourceUrl = environment.getProperty("spring.datasource.url");
        log.info("缓存类型 (akm.cacheType): {}", cacheType);
        log.info("数据源地址: {}", datasourceUrl != null ? datasourceUrl.substring(0, Math.min(50, datasourceUrl.length())) + "..." : "未配置");
        log.info("========================================");
    }
}

