package br.com.jfabiodev.TwitterClone.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record CreateUserResponseDTO(@Schema(description = "Unique identifier of the newly created user.",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") UUID userId,
                                    @Schema(description = "Username of the newly created user.", example = "jfabio") String username) {
}
