package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User Registration Request Model")
public class RegistorDto {

    @Schema(description = "Full name of the user")
    private String name;

    @Schema(description = "Unique username for authentication")
    private String username;

    @Schema(description = "User's email (must be unique)")
    private String email;

    @Schema(description = "Password (should be encrypted before saving)")
    private String password;
}
