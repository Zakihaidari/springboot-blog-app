package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Date;

@Getter
@Schema(description = "ErrorDetails Model Information")
public class ErrorDetails {

    @Schema(description = "Timestamp of the error occurrence")
    private Date timestamp;

    @Schema(description = "Error message")
    private String message;

    @Schema(description = "Detailed error description")
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
