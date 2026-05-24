package com.railways.reservation.system.security;

import com.railways.reservation.system.config.CustomAccessDeniedHandler;
import com.railways.reservation.system.config.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.railways.reservation.system.security.Roles.ROLE_ADMIN;
import static com.railways.reservation.system.security.Roles.ROLE_USER;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] PUBLIC_URLS = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/refresh",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/index.html",
    };

    private final JwtFilter jwtFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(
                config ->
                        config.requestMatchers(PUBLIC_URLS).permitAll()
                                .requestMatchers("/api/v1/stations/**").hasAuthority(ROLE_USER.name())
                                .requestMatchers("/api/v1/trains/**").hasAuthority(ROLE_ADMIN.name())
                                .requestMatchers("/api/v1/train-stops/**").hasAuthority(ROLE_ADMIN.name())
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/api/v1/bookings/**").hasAnyAuthority(ROLE_USER.name(), ROLE_ADMIN.name()
                                )
                                .anyRequest().authenticated()
        )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(ex -> ex
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)

        )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}