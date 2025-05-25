package com.ssafy.travelquest.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 1) /static/** 은 classpath:/static/ 으로
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        // 2) /uploads/** 은 C:/travelquest/uploads 으로
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///" + uploadDir.replace("\\","/") + "/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "http://localhost:8082")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
