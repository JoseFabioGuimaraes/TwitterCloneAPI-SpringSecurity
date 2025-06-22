package br.com.jfabiodev.TwitterClone.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO (@NotBlank(message = "Username should not be blank") @Schema(description = "The username used " +
        "for authentication. Must not be blank", example = "admin") String username,
                        @NotBlank(message = "Password should not be blank") @Schema(description = "The password associ" +
                                "ated with the given username. Must not be blank", example = "1234") String password) {}
