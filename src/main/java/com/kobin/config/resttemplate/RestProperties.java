package com.kobin.config.resttemplate;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Created by hongwei on 2017/5/18.
 */
@Component
@Data
@ConfigurationProperties(prefix = "http.pool")
@RefreshScope
public class RestProperties {

    private int maxTotle = 50;
    private int maxPerRoute = 200;
    private int socketTimeout = 15000;
    private int connectTimeout = 15000;
    private int connectRequestTimeout = 30000;

}






































































































































































































































































