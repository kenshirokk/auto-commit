package com.kenshirokk.dynamicdatasource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class DynamicDataSourceAutoConfiguration {

    @Autowired
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    @Bean
    public DataSource master() {
        DataSourceProp dataSourceProp = dynamicDataSourceProperties.getMaster();
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(dataSourceProp.getUrl());
        ds.setUsername(dataSourceProp.getUsername());
        ds.setPassword(dataSourceProp.getPassword());
        ds.setDriverClassName(dataSourceProp.getDriverClassName());
        return ds;
    }

    @Bean
    public List<Object> slaves() {
        List<Object> list = new ArrayList<>();
        Map<String, DataSourceProp> slaves = dynamicDataSourceProperties.getSlaves();
        if (slaves != null && slaves.values() != null && slaves.values().size() > 0) {
            for (DataSourceProp dataSourceProp : slaves.values()) {
                HikariDataSource ds = new HikariDataSource();
                ds.setJdbcUrl(dataSourceProp.getUrl());
                ds.setUsername(dataSourceProp.getUsername());
                ds.setPassword(dataSourceProp.getPassword());
                ds.setDriverClassName(dataSourceProp.getDriverClassName());
                list.add(ds);
            }
        }
        return list != null && list.size() > 0 ? list : null;
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicRoutingDataSource dynamicRoutingDataSource() {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        dataSource.setWriteDataSource(master());
        dataSource.setReadDataSources(slaves());
        return dataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dynamicRoutingDataSource());
        Resource[] resources = null;
        try {
            resources = new PathMatchingResourcePatternResolver()
                    .getResources("classpath:mapper/**/*.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        factory.setMapperLocations(resources);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        factory.setConfiguration(configuration);

        return factory.getObject();
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dynamicRoutingDataSource());
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceAop dynamicDataSourceAop() {
        return new DynamicDataSourceAop();
    }
}
