package com.kobin.config.resttemplate;

import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by shibinbin on 18/5/28.
 */
@Configuration
public class RestTemplateConfig {

    @Autowired
    private RestProperties properties;

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate(httpRequestFactory());
    }

    private HttpComponentsClientHttpRequestFactory httpRequestFactory() {
        PoolingHttpClientConnectionManager phccm = new PoolingHttpClientConnectionManager();
        phccm.setMaxTotal(properties.getMaxTotle());
        phccm.setDefaultMaxPerRoute(properties.getMaxPerRoute());
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(HttpClientBuilder.create().setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setConnectionManager(phccm).build());
        factory.setConnectionRequestTimeout(properties.getConnectRequestTimeout());
        factory.setConnectTimeout(properties.getConnectTimeout());
        factory.setReadTimeout(properties.getSocketTimeout());
        return factory;
    }

}
