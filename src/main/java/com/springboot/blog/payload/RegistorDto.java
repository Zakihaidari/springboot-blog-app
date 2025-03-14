package com.springboot.blog.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistorDto {

    private String name;        // Full name of the user
    private String username;    // Unique username for authentication
    private String email;       // User email (must be unique)
    private String password;    // Password (should be encrypted before saving)
}
