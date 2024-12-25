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
        registry.addInterceptor(loginProtectInterceptor).addPathPatterns("/user/updateUserInfo")
        .addPathPatterns("/user/getUserInfo");
        registry.addInterceptor(loginProtectInterceptor).addPathPatterns("/publicize/submit")
        .addPathPatterns("/publicize/delete")
        .addPathPatterns("/publicize/update");
        registry.addInterceptor(loginProtectInterceptor).addPathPatterns("/support/submit")
        .addPathPatterns("/support/update")
        .addPathPatterns("/support/delete/{id}")
        .addPathPatterns("/support/handle")
        .addPathPatterns("/support/mylist")
        .addPathPatterns("/support/checkPromotionUserMatch");
        // registry.addInterceptor(loginProtectInterceptor).addPathPatterns("/admin/statistics");
    }
}
