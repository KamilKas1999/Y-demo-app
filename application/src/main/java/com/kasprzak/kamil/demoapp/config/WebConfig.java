package com.kasprzak.kamil.demoapp.config;

import com.kasprzak.kamil.demoapp.interceptors.HeadersContextInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final HeadersContextInterceptor headersInterceptor;

    public WebConfig(HeadersContextInterceptor headersInterceptor) {
        this.headersInterceptor = headersInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headersInterceptor);
    }
}
