package com.example.questionapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/register", "/login", "/css/**", "/js/**").permitAll()  // Bu URL'lere herkes erişebilir
                        .anyRequest().authenticated()
                )
                .formLogin()
                .loginPage("/login")  // Login sayfası
                .loginProcessingUrl("/perform_login")  // Giriş işlemi farklı bir endpoint üzerinden yapılacak
                .defaultSuccessUrl("/home", true)  // Başarılı giriş sonrası yönlendirme
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/perform_logout")  // Çıkış işlemi
                .logoutSuccessUrl("/login")
                .permitAll();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}


