package com.userservice.security;

import com.userservice.service.UserService;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class securityconfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserService userService;
    private final Environment env;

    @Autowired
    public securityconfig(
            AuthenticationConfiguration authenticationConfiguration,
            UserService userService,
            Environment env
    ) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.userService = userService;
        this.env = env;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable);
//                .authorizeHttpRequests((request)
//                        ->request.requestMatchers("/user-service/**")
//                        .permitAll()
//                        .anyRequest().authenticated()
//                )
        http
                .authorizeHttpRequests(authorize -> authorize
                        // `/actuator/**` 경로는 인증 없이 허용
                        .requestMatchers("/actuator/**","/health-check","/users").permitAll()
                        // 다른 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                .addFilter(getAuthenticationFiler());
        http.formLogin(AbstractHttpConfigurer::disable);
        return http.build();
    }

    private Filter getAuthenticationFiler() throws Exception {
        return new AuthenticationFilter(authenticationManager(), userService, env);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return this.authenticationConfiguration.getAuthenticationManager();
    }
}
