package com.example.duantotnghiep.config;


import com.example.duantotnghiep.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/manager/hoa-don/**").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/khach-hang/**").permitAll()
                        .requestMatchers("/api/nhan-vien/**").permitAll()
                        .requestMatchers("/api/chi-tiet-sp/**").permitAll()
                        .requestMatchers("/api/gio-hang/**").permitAll()
                        .requestMatchers("/api/gio-hang-chi-tiet/**").permitAll()
                        .requestMatchers("/api/v1/hoa-don/**").permitAll()
                        .requestMatchers("/api/v1/kieu-de/**").permitAll()
                        .requestMatchers("/api/v1/mau-sac/**").permitAll()
                        .requestMatchers("/api/v1/size/**").permitAll()
                        .requestMatchers("/api/v1/thuong-hieu/**").permitAll()
                        .requestMatchers("/api/v1/danh-muc/**").permitAll()
                        .requestMatchers("/api/v1/xuat-xu/**").permitAll()
                        .requestMatchers("/api/v1/chat-lieu/**").permitAll()
                        .requestMatchers("/api/v1/san-pham-giam-gia/**").permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
