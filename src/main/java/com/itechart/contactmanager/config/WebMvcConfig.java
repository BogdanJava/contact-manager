package com.itechart.contactmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("POST", "PUT", "HEAD", "GET", "OPTIONS", "DELETE")
<<<<<<< HEAD
                .allowedOrigins("http://localhost:3000", "http://localhost:4200");
=======
                .allowedOrigins("http://localhost:3000", "http://192.168.14.233:3000");
>>>>>>> b30c18b7c7de7a314b7d0be18c24d568e01ad4b9
    }
}
