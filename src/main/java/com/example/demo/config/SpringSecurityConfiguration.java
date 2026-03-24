package com.example.demo.config;

import com.example.demo.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           DaoAuthenticationProvider authProvider) throws Exception {

        http
                .anonymous(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex
                        // 401 — не авторизован
                        .authenticationEntryPoint((request, response, authException) -> {
                            Long codeId = UUID.randomUUID().getMostSignificantBits();

                            response.setStatus(401);
                            response.setContentType("application/json;charset=UTF-8");

                            String body = """
                    {
                      "codeId": %d,
                      "message": "Пользователь не авторизован",
                      "error": "Unauthorized",
                      "status": 401,
                      "details": "Сессия отсутствует или истекла"
                    }
                    """.formatted(codeId);

                            try (PrintWriter writer = response.getWriter()) {
                                writer.write(body);
                                writer.flush();
                            }
                        })

                        // 403 — прав не хватает
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            Long codeId = UUID.randomUUID().getMostSignificantBits();

                            response.setStatus(403);
                            response.setContentType("application/json;charset=UTF-8");

                            String body = """
                    {
                      "codeId": %d,
                      "message": "Недостаточно прав доступа",
                      "error": "Forbidden",
                      "status": 403,
                      "details": "У пользователя нет необходимых прав для выполнения этого действия"
                    }
                    """.formatted(codeId);

                            try (PrintWriter writer = response.getWriter()) {
                                writer.write(body);
                                writer.flush();
                            }
                        })
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/login", "/registration",
                                "/auth/login", "/auth/logout",
                                "/debug", "/api/registration"
                        ).permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/authorsBooks")
                        .hasAnyAuthority("USER", "TECHNOLOGIST", "FULL")
                        .requestMatchers(HttpMethod.GET, "/api/books/*")
                        .hasAnyAuthority("USER", "TECHNOLOGIST", "FULL")

                        .requestMatchers(
                               "/authors_bookss"
                        ).hasAnyAuthority("USER", "TECHNOLOGIST", "FULL")
                        .requestMatchers(
                                "/api/books/**",
                                "/book/add",
                                "/book/*/edit",
                                "/book/*/delete"
                        ).hasAnyAuthority("TECHNOLOGIST", "FULL")
                        .requestMatchers(
                                "/genres",
                                "/genres/*",
                                "/genres/add",
                                "/genres/*/edit",
                                "/genres/*/delete"
                        ).hasAnyAuthority("TECHNOLOGIST", "FULL")
                        .requestMatchers(
                                "/author",
                                "/author/*",
                                "/author/add",
                                "/author/*/edit",
                                "/author/*/delete"
                        ).hasAnyAuthority("TECHNOLOGIST", "FULL")
                        .requestMatchers(
                                "/authors_bookss/**",
                                "/authorsBooks/*",
                                "/authorsBooks/add",
                                "/authorsBooks/*/edit",
                                "/authorsBooks/*/delete"
                        ).hasAnyAuthority("TECHNOLOGIST", "FULL")

                        .requestMatchers("/admin/users/**", "/api/admin/users/**").hasAuthority("FULL")

                        .anyRequest().authenticated()
                )
                .authenticationProvider(authProvider)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserServiceImpl userService,
                                                               PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider authProvider) {
        return new ProviderManager(List.of(authProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
