package com.rest_api.fs14backend.CorsConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        //Localhost
        config.addAllowedOrigin("http://127.0.0.1:5173/");
        config.addAllowedOrigin("https://stately-starship-e19365.netlify.app/");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //This will apply to all Controllers
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
