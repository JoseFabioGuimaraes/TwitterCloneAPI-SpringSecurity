package br.com.jfabiodev.TwitterClone.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
import java.util.UUID;

public record UserListResponseDTO(@Schema(description = "Unique identifier of the user", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") UUID userId,
                                  @Schema(description = "Username of the user",example = "jfabio") String username,
                                  @Schema(description = "Set of roles assigned to the user.", example = "[\"BASIC\"]") Set<String> roles) {
}
