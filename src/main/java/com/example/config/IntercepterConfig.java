package com.example.config;

import com.example.xianliu.AccessLimit;
import com.example.xianliu.AccessLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Oliver.liu
 * @version 1.0.0
 * @ClassName IntercepterConfig.java
 * @Description TODO
 * @createTime 2022年11月16日 10:51:00
 */
@Configuration
public class IntercepterConfig implements WebMvcConfigurer {
    @Autowired
    private AccessLimitInterceptor accessLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLimitInterceptor)
                .addPathPatterns("/**").excludePathPatterns("/static/**","/login.html","/user/login");
    }
}
