package com.kenshirokk.dynamicdatasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "spring.datasource.dynamic")
@Data
public class DynamicDataSourceProperties {
    private DataSourceProp master;
    private Map<String, DataSourceProp> slaves;
}
