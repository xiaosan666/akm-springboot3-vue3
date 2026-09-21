package com.akm.springboot3.core.config;

import com.alibaba.druid.DbType;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.EnumSet;
import java.util.Set;

/**
 * Druid 连接池扩展配置。
 * <p>
 * 连接池参数、StatFilter(慢SQL/SQL监控)、监控页面等直接在 application.yaml 中通过
 * {@code spring.datasource.druid.*} 配置，此处只负责 WallFilter(SQL防火墙) 的装配：
 * <ul>
 *     <li>WallFilter 依赖具体数据库方言，Druid 仅支持 MySQL、PostgreSQL、Oracle、SQLServer、DB2 等；
 *     达梦(dm) 等未支持的数据库一旦开启 wall 会直接导致 DataSource 初始化失败（dbType not support）；</li>
 *     <li>这里根据 {@code spring.datasource.url} 自动识别数据库类型，仅在 Druid 支持时才注册 WallFilter，
 *     因此切换数据库不需要改动任何配置或代码，达梦库会自动跳过并打印告警；</li>
 *     <li>防火墙规则通过 {@code spring.datasource.druid.filter.wall.config.*} 配置；</li>
 *     <li>如需强制关闭，设置 {@code akm.druid.wall.enabled=false}；
 *     注意不要使用 {@code spring.datasource.druid.filter.wall.enabled}，该属性由 Druid Starter 接管，
 *     配置后在达梦等数据库上会导致启动失败。</li>
 * </ul>
 *
 * @author akm
 */
@Slf4j
@Configuration
@ConditionalOnClass(DruidDataSource.class)
public class DruidConfig {

    /**
     * SQL 防火墙规则，前缀与 Druid Starter 保持一致，便于平滑切换
     */
    @Bean
    @ConditionalOnMissingBean
    @Conditional(WallFilterSupportedCondition.class)
    @ConfigurationProperties(prefix = "spring.datasource.druid.filter.wall.config")
    public WallConfig wallConfig() {
        return new WallConfig();
    }

    /**
     * SQL 防火墙过滤器，仅当数据库类型被 Druid 支持时装配
     */
    @Bean
    @ConditionalOnMissingBean
    @Conditional(WallFilterSupportedCondition.class)
    @ConditionalOnProperty(prefix = "akm.druid.wall", name = "enabled", havingValue = "true", matchIfMissing = true)
    public WallFilter wallFilter(WallConfig wallConfig) {
        log.info("Druid WallFilter(SQL防火墙) 已启用");
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(wallConfig);
        return wallFilter;
    }

    /**
     * 判断当前数据库类型是否被 Druid WallFilter 支持。
     * <p>
     * 判断依据是 {@code spring.datasource.url}，属于数据库类型能力判断而非业务配置，
     * 与具体项目使用何种数据库无关。
     */
    static class WallFilterSupportedCondition implements Condition {

        private static final Logger log = LoggerFactory.getLogger(WallFilterSupportedCondition.class);

        /**
         * Druid WallFilter 已支持的数据库类型，与 {@code WallFilter#init} 的分支保持一致
         */
        private static final Set<DbType> SUPPORTED_DB_TYPES = EnumSet.of(
            // MySQL 系
            DbType.mysql, DbType.mariadb, DbType.oceanbase, DbType.oceanbase_oracle, DbType.drds,
            DbType.tidb, DbType.h2, DbType.lealone, DbType.presto, DbType.trino, DbType.supersql, DbType.polardbx,
            // Oracle 系
            DbType.oracle, DbType.ali_oracle, DbType.polardb2,
            // SQLServer 系
            DbType.sqlserver, DbType.jtds,
            // PostgreSQL 系
            DbType.postgresql, DbType.edb, DbType.polardb, DbType.greenplum, DbType.gaussdb,
            // 其他
            DbType.db2, DbType.sqlite, DbType.clickhouse);

        @Override
        public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
            Environment env = context.getEnvironment();
            String url = getFirstText(env, "spring.datasource.druid.url", "spring.datasource.url");
            if (!StringUtils.hasText(url)) {
                log.warn("未获取到数据库连接地址(spring.datasource.url)，Druid WallFilter 不启用");
                return false;
            }
            String driverClassName = getFirstText(env,
                "spring.datasource.druid.driver-class-name", "spring.datasource.driver-class-name");
            DbType dbType = JdbcUtils.getDbTypeRaw(url, driverClassName);
            if (dbType != null && SUPPORTED_DB_TYPES.contains(dbType)) {
                return true;
            }
            log.warn("数据库类型[{}]不受 Druid WallFilter 支持（如达梦 dm），已自动跳过 SQL 防火墙，"
                + "连接池与 SQL/慢SQL 监控不受影响；如需防火墙能力，请通过 SPI 扩展 "
                + "com.alibaba.druid.wall.spi.WallProviderCreator", dbType);
            return false;
        }

        private @Nullable String getFirstText(Environment env, String... keys) {
            for (String key : keys) {
                String value = env.getProperty(key);
                if (StringUtils.hasText(value)) {
                    return value;
                }
            }
            return null;
        }
    }
}
