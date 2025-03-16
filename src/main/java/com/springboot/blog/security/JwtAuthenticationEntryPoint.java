package com.springboot.blog.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This class handles unauthorized access attempts.
 * It implements AuthenticationEntryPoint, which is triggered when an unauthenticated user
 * tries to access a protected resource.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * This method is called whenever an unauthenticated user requests a secured endpoint.
     * It sends a 401 Unauthorized response with an error message.
     *
     * @param request       - The incoming HTTP request.
     * @param response      - The outgoing HTTP response.
     * @param authException - The authentication exception that caused the failure.
     * @throws IOException      - If an input or output error occurs while handling the request.
     * @throws ServletException - If the request could not be handled.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // Send a 401 Unauthorized response with the exception message
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
