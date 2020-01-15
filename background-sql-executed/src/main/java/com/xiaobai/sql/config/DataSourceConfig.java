package com.xiaobai.sql.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author XinHuiChen
 */
@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSourceConfig")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "selectUpdateDataSource")
    @Qualifier("selectUpdateDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.select-update")
    public DataSource selectUpdateDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "deleteInsertDataSource")
    @Qualifier("deleteInsertDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.delete-insert")
    public DataSource deleteInsertDataSource() {
        return DataSourceBuilder.create().build();
    }

}
