package com.rest_api.fs14backend.SecurityConfig;

import com.rest_api.fs14backend.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/books/", "api/v1/authors/","/api/v1/books/byAuthor/{id}","/api/v1/books/list/*", "/api/v1/signup", "/api/v1/book-copies/{id}", "/api/v1/signin", "/api/v1/images/upload/")
                .permitAll()
                .requestMatchers(HttpMethod.GET,"/api/v1/categories/")
                .permitAll()
                .requestMatchers("/api/v1/authors/").hasRole("USER")
                .requestMatchers("/api/v1/users/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/checkouts/borrow/").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/v1/checkouts/return/").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/v1/categories/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/book-copies/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/authors/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/books/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/books/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/authors/{id}").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Add JWT token filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}