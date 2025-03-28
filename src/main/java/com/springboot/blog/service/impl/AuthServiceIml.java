package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegistorDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceIml implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceIml(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * User login functionality.
     * Validates user credentials and generates a JWT token.
     *
     * @param loginDto - Contains username/email and password.
     * @return JWT Token upon successful authentication.
     *
     * POST Request: http://localhost:8080/api/auth/login
     */
    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );

        // Set authentication in security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate and return JWT token
        return jwtTokenProvider.generateToken(authentication);
    }

    /**
     * User registration functionality.
     * Validates if username and email already exist before creating a new user.
     *
     * @param registorDto - Contains new user details.
     * @return Success message upon successful registration.
     *
     * POST Request: http://localhost:8080/api/auth/register
     */
    @Override
    public String registor(RegistorDto registorDto) {

        // Check if username already exists
        if (userRepository.existsByUsername(registorDto.getUsername())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(registorDto.getEmail())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        // Create a new user entity
        User user = new User();
        user.setName(registorDto.getName());
        user.setUsername(registorDto.getUsername());
        user.setEmail(registorDto.getEmail());
        user.setPassword(passwordEncoder.encode(registorDto.getPassword())); // Encrypt password before saving

        // Assign default role "ROLE_USER" to new users
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(
                () -> new BlogApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Role not found!")
        );
        roles.add(userRole);
        user.setRoles(roles);

        // Save the new user to the database
        userRepository.save(user);

        return "User Registered Successfully!";
    }
}
