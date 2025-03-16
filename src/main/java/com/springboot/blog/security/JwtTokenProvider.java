package com.springboot.blog.security;

import com.springboot.blog.exception.BlogApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Secret key used for signing the JWT (fetched from application properties)
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    // Expiration time for the JWT (fetched from application properties)
    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    /**
     * Generates a JWT token based on the authenticated user.
     *
     * @param authentication - The authentication object containing user details.
     * @return A signed JWT token as a String.
     */
    public String generateToken(Authentication authentication) {
        String username = authentication.getName(); // Extract username from authentication
        Date currentDate = new Date(); // Get current timestamp
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate); // Set expiration time

        // Create and return a signed JWT token
        return Jwts.builder()
                .subject(username)  // Set username as subject
                .issuedAt(currentDate)  // Set token issuance time
                .expiration(expireDate)  // Set expiration time
                .signWith(key())  // Sign the token with the secret key
                .compact();  // Generate the compact JWT string
    }

    /**
     * Generates a secret key for signing and verifying JWTs.
     *
     * @return A Key object created using the secret key.
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)); // Decode and return the secret key
    }

    /**
     * Extracts the username (subject) from a JWT token.
     *
     * @param token - The JWT token.
     * @return The username stored in the token.
     */
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())  // Verify the token with the secret key
                .build()
                .parseSignedClaims(token)  // Parse the JWT claims
                .getPayload()
                .getSubject();  // Extract and return the username (subject)
    }

    /**
     * Validates a JWT token.
     *
     * @param token - The JWT token to validate.
     * @return true if the token is valid, otherwise an exception is thrown.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())  // Verify the token signature
                    .build()
                    .parse(token);  // Parse the token
            return true;  // Return true if the token is valid
        } catch (MalformedJwtException ex) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Token"); // Token is invalid
        } catch (ExpiredJwtException ex) {
            throw new BlogApiException(HttpStatus.UNAUTHORIZED, "Expired JWT Token"); // Token is expired
        } catch (UnsupportedJwtException ex) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token"); // Token is unsupported
        } catch (IllegalArgumentException ex) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty"); // Token is empty
        }
    }
}
