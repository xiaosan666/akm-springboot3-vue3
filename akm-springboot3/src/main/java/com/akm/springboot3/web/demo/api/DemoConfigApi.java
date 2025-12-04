package com.akm.springboot3.web.demo.api;

import com.akm.springboot3.core.config.DynamicConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring Cloud Config 配置测试接口
 *
 * @author akm
 */
@Tag(name = "配置中心测试")
@RestController
@RequestMapping("/demo/config")
@RefreshScope  // 支持配置动态刷新
@ConditionalOnProperty(name = "spring.cloud.config.enabled", havingValue = "true", matchIfMissing = false)
public class DemoConfigApi {

    private final ConfigurableEnvironment environment;
    private final DynamicConfig dynamicConfig;

    @Value("${spring.cloud.config.uri:未配置}")
    private String configServerUri;

    @Value("${spring.application.name:未配置}")
    private String applicationName;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @Value("${akm.cacheType:未配置}")
    private String cacheType;

    @Value("${akm.enabledAuth:未配置}")
    private String enabledAuth;

    @Value("${spring.datasource.url:未配置}")
    private String datasourceUrl;

    public DemoConfigApi(ConfigurableEnvironment environment, DynamicConfig dynamicConfig) {
        this.environment = environment;
        this.dynamicConfig = dynamicConfig;
    }

    @Operation(summary = "查看当前配置信息", description = "验证是否从 Config Server 获取配置")
    @GetMapping("/open/info")
    public Map<String, Object> getConfigInfo() {
        Map<String, Object> result = new HashMap<>();
        
        // Config Server 信息
        result.put("configServerUri", configServerUri);
        result.put("applicationName", applicationName);
        result.put("activeProfile", activeProfile);
        
        // 检查配置来源
        boolean fromConfigServer = environment.getPropertySources().stream()
            .anyMatch(ps -> ps.getName().contains("configClient") || ps.getName().contains("Config resource"));
        result.put("fromConfigServer", fromConfigServer);
        
        // 示例配置值
        Map<String, String> sampleConfigs = new HashMap<>();
        sampleConfigs.put("akm.cacheType", cacheType);
        sampleConfigs.put("akm.enabledAuth", enabledAuth);
        sampleConfigs.put("spring.datasource.url", maskSensitiveInfo(datasourceUrl));
        result.put("sampleConfigs", sampleConfigs);
        
        // 配置源列表
        String[] propertySources = environment.getPropertySources().stream()
            .map(ps -> ps.getName())
            .filter(name -> !name.startsWith("servlet") && !name.startsWith("system"))
            .limit(10)
            .toArray(String[]::new);
        result.put("propertySourcesTop10", propertySources);
        
        return result;
    }

    @Operation(summary = "测试配置刷新", description = "查看支持动态刷新的配置当前值")
    @GetMapping("/open/refresh-test")
    public Map<String, Object> testRefresh() {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "当前配置值（可通过 POST /actuator/refresh 刷新）");
        result.put("refreshInstructions", "执行命令: curl -X POST http://localhost:33000/actuator/refresh");
        
        // 从 @RefreshScope Bean 中获取配置
        Map<String, Object> dynamicConfigs = new HashMap<>();
        dynamicConfigs.put("cacheType", dynamicConfig.getCacheType());
        dynamicConfigs.put("enabledAuth", dynamicConfig.getEnabledAuth());
        dynamicConfigs.put("enableLoginCaptcha", dynamicConfig.getEnableLoginCaptcha());
        dynamicConfigs.put("forcePasswordChange", dynamicConfig.getForcePasswordChange());
        dynamicConfigs.put("enabledCorsAllow", dynamicConfig.getEnabledCorsAllow());
        dynamicConfigs.put("enabledEncrypt", dynamicConfig.getEnabledEncrypt());
        dynamicConfigs.put("enabledSign", dynamicConfig.getEnabledSign());
        dynamicConfigs.put("enabledApiFreq", dynamicConfig.getEnabledApiFreq());
        
        result.put("dynamicConfigs", dynamicConfigs);
        return result;
    }

    @Operation(summary = "触发配置刷新", description = "手动触发配置刷新并记录日志")
    @PostMapping("/open/trigger-refresh-log")
    public Map<String, String> triggerRefreshLog() {
        dynamicConfig.logRefresh();
        Map<String, String> result = new HashMap<>();
        result.put("message", "配置刷新日志已记录，请查看控制台输出");
        result.put("tip", "实际刷新需要调用: POST /actuator/refresh");
        return result;
    }

    /**
     * 屏蔽敏感信息
     */
    private String maskSensitiveInfo(String info) {
        if (info == null || info.length() < 20) {
            return "***";
        }
        return info.substring(0, Math.min(50, info.length())) + "...";
    }
}

