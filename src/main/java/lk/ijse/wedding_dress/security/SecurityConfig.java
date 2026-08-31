package lk.ijse.wedding_dress.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    // =========================================================
    // PASSWORD ENCODER
    // =========================================================
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // =========================================================
    // AUTHENTICATION PROVIDER
    // =========================================================
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(customUserDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }


    // =========================================================
    // AUTHENTICATION MANAGER
    // =========================================================
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }


    // =========================================================
    // SECURITY FILTER CHAIN
    // =========================================================
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

                // Disable CSRF because JWT is being used
                .csrf(csrf -> csrf.disable())


                // JWT is stateless
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )


                // Authorization rules
                .authorizeHttpRequests(auth -> auth

                        // =====================================
                        // PUBLIC ENDPOINTS
                        // =====================================
                        .requestMatchers(
                                "/api/auth/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/",
                                "/login",
                                "/register"
                        ).permitAll()


                        // =====================================
                        // ADMIN ONLY
                        // =====================================
                        .requestMatchers("/api/admin/**")
                        .hasAuthority("ADMIN")


                        // =====================================
                        // USER + ADMIN
                        // =====================================
                        .requestMatchers("/api/user/**")
                        .hasAnyAuthority("USER", "ADMIN")


                        // =====================================
                        // OTHER ENDPOINTS
                        // =====================================
                        .anyRequest()
                        .authenticated()
                )


                // Authentication Provider
                .authenticationProvider(
                        authenticationProvider()
                )


                // JWT Authentication Filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );


        return http.build();
    }
}

