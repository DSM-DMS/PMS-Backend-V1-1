package com.dms.pms.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "dmsEntityManagerFactory",
        transactionManagerRef = "dmsTransactionManager",
        basePackages = { "com.dms.pms.entity.dms" }
)
public class DmsDbConfig {
    @Bean(name = "dmsDataSource")
    @ConfigurationProperties(prefix = "dms.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dmsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dmsDataSource") DataSource dataSource
    ){
        return builder
                .dataSource(dataSource)
                .packages("com.dms.pms.entity.dms")
                .persistenceUnit("dms")
                .build();
    }

    @Bean(name = "dmsTransactionManager")
    public PlatformTransactionManager dmsTransactionManager(
            @Qualifier("dmsEntityManagerFactory") EntityManagerFactory dmsEntityManagerFactory
    ){
        return new JpaTransactionManager(dmsEntityManagerFactory);
    }
}
