package com.example.bpmsenterprise.configs;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

public class CorsConfig implements CorsConfigurationSource {
    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

        List<String> listOfOrigin = List.of("http://localhost:8080/");
        List<String> listOfHttpMethod = List.of("GET", "POST", "PUT", "DELETE");

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(listOfOrigin);
        config.setAllowedMethods(listOfHttpMethod);

        return config;
    }
}
