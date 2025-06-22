package br.com.jfabiodev.TwitterClone.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponseDTO(@Schema(description ="The JWT token issued after successful authentication. " +
        "Used to authorize requests to protected endpoints.", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...") String accessToken,
                               @Schema(description = "Time in seconds until the access token expires.", example = "3600") Long expiresIn) {
}
