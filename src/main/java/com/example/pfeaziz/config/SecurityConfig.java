package com.example.pfeaziz.config;

import com.example.pfeaziz.security.JwtAuthenticationFilter;
import com.example.pfeaziz.security.JwtAuthorizationFilter;
import com.example.pfeaziz.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS + CSRF
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())

                // Sessions stateless
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Autorisations
                .authorizeHttpRequests(auth -> auth
                        // endpoints publics
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/machines/**").permitAll()
                        .requestMatchers("/api/demandes").permitAll()
                        .requestMatchers("/api/demandes/**").permitAll()
                        .requestMatchers("/api/ilots/**").permitAll()
                        .requestMatchers("/api/programmes/**").permitAll()
                        .requestMatchers("/api/demandes_finale/**",
                                "/api/demandes_delegue/**",
                                "/api/demandes_te/**").permitAll()

                        // accès restreint
                        .requestMatchers("/pages/table-admin").hasRole("admin")
                        .requestMatchers("/pages/table-controleur").hasRole("controleur")
                        .requestMatchers("/pages/table-operateur").hasRole("operateur")
                        .requestMatchers("/pages/table-manager").hasRole("manager")

                        // le reste, authentification requise
                        .anyRequest().authenticated()
                )

                // Ajout des filtres JWT
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /** Pour autoriser l’accès à la console H2, etc. */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web
                .ignoring()
                .requestMatchers("/h2-console/**");
    }

    /** Configuration CORS */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Authorization"));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /** Password encoder */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
