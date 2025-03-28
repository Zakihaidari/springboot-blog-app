package com.springboot.blog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT Authentication Filter:
 * - Intercepts incoming requests.
 * - Extracts and validates JWT tokens.
 * - If valid, sets user authentication in the SecurityContext.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    /**
     * Constructor injection for dependencies.
     * @param jwtTokenProvider - Utility class for JWT operations.
     * @param userDetailsService - Service to load user details from database.
     */
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    /**
     * This method is called for each HTTP request.
     * It checks for JWT tokens and sets authentication if the token is valid.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extract JWT token from the request
        String token = getTokenFromRequest(request);

        // Validate token and set authentication in the security context
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            // Extract username from token
            String username = jwtTokenProvider.getUsername(token);

            // Load user details from the database
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Create authentication token
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // Set additional authentication details (like IP address)
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Store authentication in SecurityContext (Spring Security)
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts JWT token from the request headers.
     * @param request - HTTP request
     * @return JWT token if present, otherwise null
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        // Get Authorization header (e.g., "Bearer <TOKEN>")
        String bearerToken = request.getHeader("Authorization");

        // Check if the header is not empty and starts with "Bearer "
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            // Extract token after "Bearer " (7 characters)
            return bearerToken.substring(7);
        }
        return null;
    }
}
