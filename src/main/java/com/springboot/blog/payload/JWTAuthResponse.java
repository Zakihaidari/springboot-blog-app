package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "JWT Authentication Response Model")
public class JWTAuthResponse {

    @Schema(description = "JWT access token")
    private String accessToken;

    @Schema(description = "Type of the token, usually 'Bearer'")
    private String tokenType = "Bearer";
}
