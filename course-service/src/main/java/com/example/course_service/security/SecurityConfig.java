package com.example.course_service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Put permitAll FIRST before any role-based rules
                        .requestMatchers("/api/health", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
                        // Student can view subjects, topics, learning materials
                        .requestMatchers(HttpMethod.GET, "/api/subjects/**").hasAnyRole("STUDENT", "TEACHER")
                        .requestMatchers(HttpMethod.GET, "/api/topics/**").hasAnyRole("STUDENT", "TEACHER")
                        .requestMatchers(HttpMethod.GET, "/api/materials/**").hasAnyRole("STUDENT", "TEACHER")
                        .requestMatchers(HttpMethod.GET, "/api/questions/**").hasAnyRole("STUDENT", "TEACHER")
                        // Student can add feedback
                        .requestMatchers(HttpMethod.POST, "/api/feedbacks/**").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/feedbacks/**").hasRole("TEACHER")
                        // Teacher can create/update/delete topics and materials
                        .requestMatchers(HttpMethod.POST, "/api/subjects/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/api/subjects/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/api/subjects/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.POST, "/api/topics/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/api/topics/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/api/topics/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.POST, "/api/materials/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/api/materials/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/api/materials/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.POST, "/api/questions/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/api/questions/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/api/questions/**").hasRole("TEACHER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
