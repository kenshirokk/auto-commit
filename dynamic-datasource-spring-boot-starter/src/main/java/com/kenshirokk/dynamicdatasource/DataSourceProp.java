package com.kenshirokk.dynamicdatasource;

import lombok.Data;

@Data
public class DataSourceProp {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
