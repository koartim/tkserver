package com.timkoar.tkserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .headers(headers -> headers
                        .addHeaderWriter(new StaticHeadersWriter("X-XSS-Protection", "1; mode=block")) // XSS protection
                        .frameOptions(frameOptions -> frameOptions
                                .deny()  // Prevent clickjacking by not allowing the site to be framed
                        )
                        .addHeaderWriter(new StaticHeadersWriter("X-Content-Type-Options", "nosniff"))
                        .addHeaderWriter(new StaticHeadersWriter("Strict-Transport-Security", "max-age=31536000; includeSubDomains"))
                )
                .authorizeHttpRequests(authorize -> {
                    authorize
                        .requestMatchers("/api/**").permitAll() // Allow access to /api/contact
                        .anyRequest().authenticated();
                })
                .httpBasic().disable() // Disable HTTP Basic authentication to prevent login prompts
                .formLogin().disable() // Disable form-based authentication to prevent login prompts
                .cors().and();
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(
                List.of("Content-Type", "x-xsrf-token", "Authorization", "X-Content-Type-Options","X-Frame-Options",
                        "X-XSS-Protection", "Strict-Transport-Security"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
