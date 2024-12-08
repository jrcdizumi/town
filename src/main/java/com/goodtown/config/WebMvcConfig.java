package com.goodtown.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.goodtown.interceptors.LoginProtectInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginProtectInterceptor loginProtectInterceptor;

    @Override
    public void addInterceptors(@SuppressWarnings("null") InterceptorRegistry registry) {
        registry.addInterceptor(loginProtectInterceptor).addPathPatterns("/user/updateUserInfo");
        registry.addInterceptor(loginProtectInterceptor).addPathPatterns("/user/getUserInfo");
    }
}
