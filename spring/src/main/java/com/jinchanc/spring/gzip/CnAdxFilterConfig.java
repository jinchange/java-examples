package com.jinchanc.spring.gzip;

import jakarta.annotation.Resource;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 475636591@qq.com
 * @since 2024/12/13 12:04
 */

@Configuration
public class CnAdxFilterConfig {

    @Resource
    private WebServerFilter webServerFilter;

    @Bean
    public FilterRegistrationBean<WebServerFilter> myFilterRegistration() {
        FilterRegistrationBean<WebServerFilter> registration = new FilterRegistrationBean<>(webServerFilter);
        registration.addUrlPatterns("/*");
        return registration;
    }
}
