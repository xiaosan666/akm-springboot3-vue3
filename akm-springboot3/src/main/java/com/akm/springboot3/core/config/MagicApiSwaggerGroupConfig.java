package com.akm.springboot3.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Configuration;

/**
 * 修正 magic-api 在接口文档（/doc.html）中的分组，最终只保留：
 * 系统管理API、DEMO API、公共模块API、magic-api。详见 application.yaml 中 springdoc 段的说明。
 *
 * <p>背景（magic-api-plugin-springdoc 的注入规则）：
 * 该插件在 springdoc 的 {@link SwaggerUiConfigProperties#getUrls()} 为 null 时，
 * 会往 springdoc.swagger-ui.urls 中注入两个条目：
 * <ul>
 *   <li>default &rarr; springdoc.api-docs.path（即 /akm-docs，未分组的“全量”文档，
 *       因此它包含了三个业务分组的全部接口）</li>
 *   <li>magic-api &rarr; 取自 magic-api.springdoc.location（即 /akm-docs/magic-api/swagger2.json）</li>
 * </ul>
 * 并且 /akm-docs/magic-api/swagger2.json 这个 Spring MVC 映射，是插件在它注入的那条 magic-api 条目
 * 的 url 被读取时才懒注册的（springdoc 生成 swagger-config 时会 cloneUrls()，从而触发注册）。
 *
 * <p>所以这里不能用 yaml 手写 springdoc.swagger-ui.urls 来控制分组：
 * 一旦手写，Spring Boot 的配置绑定会覆盖掉插件的注入，导致 magic-api 分组丢失、
 * swagger2.json 因映射未注册而返回 100404「未找到处理方法」。
 * 正确做法是保留插件的注入，再把插件多注入的 default 条目摘掉。
 *
 * @author xiaojun
 */
@Configuration
public class MagicApiSwaggerGroupConfig implements SmartInitializingSingleton {

    private static final Logger log = LoggerFactory.getLogger(MagicApiSwaggerGroupConfig.class);

    /**
     * magic-api 插件注入的“全量文档”分组名
     */
    private static final String DEFAULT_GROUP_NAME = "default";

    private final ObjectProvider<SwaggerUiConfigProperties> swaggerUiConfigProvider;

    MagicApiSwaggerGroupConfig(ObjectProvider<SwaggerUiConfigProperties> swaggerUiConfigProvider) {
        this.swaggerUiConfigProvider = swaggerUiConfigProvider;
    }

    /**
     * 单例全部实例化之后（此时 magic-api 插件已经完成 urls 注入，且早于任何一次
     * /akm-docs/swagger-config 请求）摘除插件多注入的 default 分组。
     * <p>注意：springdoc 每次响应 swagger-config 都会基于 SwaggerUiConfigProperties
     * 新建 SwaggerUiConfigParameters，所以这里必须改 SwaggerUiConfigProperties 本身。
     */
    @Override
    public void afterSingletonsInstantiated() {
        swaggerUiConfigProvider.stream().forEach(swaggerUiConfig -> {
            if (swaggerUiConfig.getUrls() == null) {
                return;
            }
            boolean removed = swaggerUiConfig.getUrls().removeIf(url -> DEFAULT_GROUP_NAME.equals(url.getName()));
            if (removed) {
                log.info(">> 已移除 magic-api 插件注入的 [{}] 分组，当前接口文档分组：{}", DEFAULT_GROUP_NAME,
                    swaggerUiConfig.getUrls().stream().map(url -> url.getDisplayName()).toList());
            }
        });
    }
}
