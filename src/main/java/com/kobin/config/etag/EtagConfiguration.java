package com.kobin.config.etag;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

/**
 * Created by @author shibinbin on 2018/6/3.
 */

public class EtagConfiguration extends WebMvcAutoConfiguration {
    @Bean
    public ShallowEtagHeaderFilter etagHeaderFilter(){
        return new ShallowEtagHeaderFilter();
    }
}
