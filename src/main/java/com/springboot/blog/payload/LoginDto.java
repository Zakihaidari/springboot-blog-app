package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Login Request Model")
public class LoginDto {

    @Schema(description = "Username or Email for login")
    private String usernameOrEmail;

    @Schema(description = "User's password")
    private String password;
}
