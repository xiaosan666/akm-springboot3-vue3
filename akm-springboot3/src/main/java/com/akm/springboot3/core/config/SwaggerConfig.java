package com.akm.springboot3.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI配置，接口文档
 * 说明：SpringDoc 不支持通过 YAML 配置 API 基本信息，必须使用 Java 代码
 * https://springdoc.org/
 *
 * @author xiaojun
 *
 */
@Configuration
public class SwaggerConfig {

    /**
     * 配置 API 基本信息和全局 Security Scheme
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("akm-springboot API")
                .description("""
                    本文档由 SpringDoc 自动生成。
                    如何在此页面调试接口？
                    1. 确保后台服务已经屏蔽接口加解密校验和签名校验
                    2. 使用[Base Api]中的登录接口获得token
                    3. 点击页面右上角 Authorize 按钮，输入 token
                    4. 请求头Authorize设置成功后，即可访问该token有权访问的接口
                    """)
                .version("3.0.0"))
            .addSecurityItem(new SecurityRequirement().addList("Authorization"))
            .components(new Components()
                .addSecuritySchemes("Authorization", new SecurityScheme()
                    .name("Authorization")
                    .type(SecurityScheme.Type.APIKEY)
                    .in(SecurityScheme.In.HEADER)
                    .description("Authorization token")));
    }

    /**
     * 系统管理 API 分组
     */
    @Bean
    public GroupedOpenApi systemApi() {
        return GroupedOpenApi.builder()
            .group("系统管理API")
            .packagesToScan("com.akm.springboot3.web.sys")
            .build();
    }

    /**
     * DEMO API 分组
     */
    @Bean
    public GroupedOpenApi demoApi() {
        return GroupedOpenApi.builder()
            .group("DEMO API")
            .packagesToScan("com.akm.springboot3.web.demo")
            .build();
    }

    /**
     * 公共模块 API 分组
     */
    @Bean
    public GroupedOpenApi bizApi() {
        return GroupedOpenApi.builder()
            .group("公共模块API")
            .packagesToScan("com.akm.springboot3.web.biz")
            .build();
    }
}
