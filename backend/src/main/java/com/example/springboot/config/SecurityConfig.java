package com.example.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/files/**").permitAll() //后端资源允许所有人访问
                        .requestMatchers("/auth/login").permitAll()   // 新增 admin 角色权限
                        .requestMatchers("/auth/staff").permitAll()// 更新admin角色权限
                        .requestMatchers("/auth/").hasRole("Admin")
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}




