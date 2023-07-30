package com.example.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(
                jwtAuthFilter,
                CorsFilter.class
        );
        http
                .cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                .antMatchers("/users/signup", "/users/login", "/theaters", "/theaters/**", "/schedules/**").permitAll()
                .antMatchers(HttpMethod.GET, "/movies", "/seats").permitAll()
                .antMatchers(HttpMethod.PATCH, "/users/{id}", "/theaters").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.POST, "/seats", "/theaters", "/schedules").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.DELETE, "/theaters", "/schedules", "/movies").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/movies/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/users/**", "/payments", "/payments/**").authenticated()
        ;
        return http.build();
    }

}
