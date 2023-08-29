package com.rest_api.fs14backend.corsConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://127.0.0.1:5173/");
        configuration.addAllowedOrigin("https://fs14-backend-6whg.onrender.com/api/");


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //Books and authors
        source.registerCorsConfiguration("/api/books/**", configuration);
        source.registerCorsConfiguration("/api/authors/**", configuration);

        return source;
    }
}