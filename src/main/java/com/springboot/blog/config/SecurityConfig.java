package com.springboot.blog.config;

import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig is the main class for configuring Spring Security.
 * It:
 * - Defines authentication and authorization rules.
 * - Configures JWT-based authentication.
 * - Disables session-based authentication.
 */
@Configuration  // Marks this class as a configuration class for Spring Security.
@EnableMethodSecurity  // Enables method-level security (e.g., @PreAuthorize).
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter authenticationFilter;

    /**
     * Constructor-based dependency injection for security components.
     * @param userDetailsService Service that loads user details from DB.
     * @param authenticationEntryPoint Handles unauthorized access responses.
     * @param authenticationFilter JWT filter for authenticating API requests.
     */
    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint authenticationEntryPoint,
                          JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    /**
     * Defines password encoding mechanism.
     * @return A BCryptPasswordEncoder for securely hashing passwords.
     */
    @Bean
    public static PasswordEncoder passwordEncode() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides the authentication manager.
     * Used to authenticate user credentials.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Configures HTTP security:
     * - Disables CSRF (since we're using JWT, not cookies).
     * - Defines access rules for API endpoints.
     * - Enables exception handling.
     * - Configures stateless authentication (no sessions).
     * - Registers the JWT authentication filter.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // Disable CSRF protection (not needed for REST APIs)
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()  // Public access for GET requests
                                .requestMatchers("/api/auth/**").permitAll()  // Public access for authentication endpoints
                                .anyRequest().authenticated()  // Other requests require authentication
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint))  // Handles unauthorized access
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // No session management (stateless JWT authentication)

        // Add JWT Authentication Filter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Creates in-memory users for testing.
     * (Commented out since we're using a database for authentication).
     */
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails zaki = User.builder()
//                .username("zaki")
//                .password(passwordEncode().encode("zaki"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncode().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(zaki,admin);
//    }
}
