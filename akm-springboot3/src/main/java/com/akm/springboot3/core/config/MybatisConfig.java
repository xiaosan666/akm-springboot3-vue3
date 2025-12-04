package com.akm.springboot3.core.config;

import com.akm.springboot3.core.interceptor.mybatis.MyBatisSqlInterceptor;
import com.akm.springboot3.core.interceptor.mybatis.PrintSqlInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@Slf4j
public class MybatisConfig {

    @Bean
    public DatabaseIdProvider databaseIdProvider() {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties p = new Properties();
        p.setProperty("MySQL", "mysql");
        p.setProperty("PostgreSQL", "pg");
        p.setProperty("DM DBMS", "dm");
        databaseIdProvider.setProperties(p);
        return databaseIdProvider;
    }

    @Bean
    @ConditionalOnProperty(name = "akm.mybatis.print-sql", havingValue = "true")
    public PrintSqlInterceptor printSqlInterceptor() {
        log.info("akm mybatis register {}", PrintSqlInterceptor.class.getName());
        return new PrintSqlInterceptor(10L * 1000);
    }

    @Bean
    public MyBatisSqlInterceptor myBatisSqlInterceptor() {
        return new MyBatisSqlInterceptor();
    }
}
