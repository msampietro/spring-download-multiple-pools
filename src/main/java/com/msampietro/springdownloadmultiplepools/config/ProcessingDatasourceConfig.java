package com.msampietro.springdownloadmultiplepools.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.metadata.HikariDataSourcePoolMetadata;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "processingEntityManagerFactory",
        transactionManagerRef = "processingTransactionManager",
        basePackages = "com.msampietro.springdownloadmultiplepools.module")
public class ProcessingDatasourceConfig {

    @SuppressWarnings("unchecked")
    @Bean(name = "processingDataSource")
    public DataSource processingDataSource(@Qualifier("dataSourceProperties") Map<String, Object> dataSourcePropertiesHolder) {
        Map<String, Object> dataSourceProperties = (Map<String, Object>) dataSourcePropertiesHolder.get("dataSourceProperties");
        var config = MainDatasourceConfig.buildHikariConfig(dataSourceProperties);
        config.setMaximumPoolSize(config.getMinimumIdle());
        config.setPoolName("HikariPool-Processing");
        config.getDataSourceProperties().remove("socketTimeout");
        return new HikariDataSource(config);
    }

    @Bean
    public HikariDataSourcePoolMetadata processingHikariDataSourcePoolMetadata(@Qualifier("processingDataSource") DataSource dataSource) {
        return new HikariDataSourcePoolMetadata((HikariDataSource) dataSource);
    }

    @Bean(name = "processingEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean processingEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                                 @Qualifier("processingDataSource") DataSource processingDataSource,
                                                                                 @Qualifier("jpaProperties") Map<String, String> jpaProperties) {
        return builder
                .dataSource(processingDataSource)
                .persistenceUnit("processing")
                .packages("com.msampietro.springdownloadmultiplepools.module")
                .properties(jpaProperties)
                .build();
    }

    @Bean(name = "processingTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("processingEntityManagerFactory") EntityManagerFactory processingEntityManagerFactory) {
        return new JpaTransactionManager(processingEntityManagerFactory);
    }

}
