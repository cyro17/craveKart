package com.cyro.craveKart.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.sessionManagement(session-> session
                .sessionCreationPolicy((SessionCreationPolicy.STATELESS)))
                .authorizeHttpRequests(requests->
                        requests.requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER", "ADMIN")
                                .requestMatchers("/api/**").authenticated()
                                .anyRequest().permitAll()
                )
                .csrf(Customizer->Customizer.disable())
                .cors(cors->cors.configurationSource(corsConfigurationSource()));

        return null;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(Arrays.asList(
                        "http://localhost:8080"
                ));
                corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
                corsConfiguration.setMaxAge(3600L);

                return  corsConfiguration;
            }
        };
    }
}
