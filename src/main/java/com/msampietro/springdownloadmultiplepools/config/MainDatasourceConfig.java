package com.msampietro.springdownloadmultiplepools.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource({"classpath:application.properties"})
public class MainDatasourceConfig {

    @SuppressWarnings("unchecked")
    protected static HikariConfig buildHikariConfig(Map<String, Object> dataSourceProperties) {
        Map<String, Object> hikariProperties = (Map<String, Object>) dataSourceProperties.get("hikari");
        Map<String, Object> hikariDataSourceProperties = (Map<String, Object>) hikariProperties.get("data-source-properties");
        var config = new HikariConfig();
        config.setPoolName("HikariPool-1-Main");
        config.setJdbcUrl(dataSourceProperties.get("url").toString());
        config.setDriverClassName(dataSourceProperties.get("driverClassName").toString());
        config.setUsername(dataSourceProperties.get("username").toString());
        config.setPassword(dataSourceProperties.get("password").toString());
        config.setMaximumPoolSize(Integer.parseInt(hikariProperties.get("maximum-pool-size").toString()));
        config.setMinimumIdle(Integer.parseInt(hikariProperties.get("minimum-idle").toString()));
        config.setIdleTimeout(Long.parseLong(hikariProperties.get("idle-timeout").toString()));
        config.setConnectionTimeout(Long.parseLong(hikariProperties.get("connection-timeout").toString()));
        config.setMaxLifetime(Long.parseLong(hikariProperties.get("max-lifetime").toString()));
        var properties = MapUtils.toProperties(hikariDataSourceProperties);
        config.setDataSourceProperties(properties);
        return config;
    }

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        var hikariConfig = buildHikariConfig(getDataSourceProperties());
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "dataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource")
    public Map<String, Object> getDataSourceProperties() {
        return new HashMap<>();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("dataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.msampietro.springdownloadmultiplepools.module")
                .properties(getJpaProperties())
                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean("jpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa")
    public Map<String, String> getJpaProperties() {
        return new HashMap<>();
    }

}
