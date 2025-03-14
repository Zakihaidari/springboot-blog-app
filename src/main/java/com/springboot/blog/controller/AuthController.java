package com.springboot.blog.controller;


import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegistorDto;
import com.springboot.blog.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Marks this class as a REST controller, allowing it to handle HTTP requests
@RequestMapping("/api/auth") // Base URL for all endpoints in this controller
public class AuthController {

    private AuthService authService; // Service layer dependency for handling authentication logic

    // Constructor-based dependency injection for AuthService
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Handles user login requests.
     *
     * This endpoint allows users to authenticate using their credentials.
     * It supports both "/login" and "/signin" as valid request paths.
     *
     * @param loginDto Object containing user login details (username/email and password).
     * @return A response containing an authentication token or an error message.
     */
    @PostMapping(value = {"/login", "/signin"}) // Maps requests for login authentication
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String response = authService.login(loginDto); // Calls the service to authenticate the user
        return ResponseEntity.ok(response); // Returns the authentication response
    }


    //Build Register  Rest API

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> registor(@RequestBody RegistorDto registorDto){
        String response = authService.registor(registorDto);
        return ResponseEntity.ok(response);

    }
}
