package br.com.jfabiodev.TwitterClone.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO (@NotBlank(message = "Username should not be blank") @Schema(
        description = "The desired username for the new user. Must be unique and not blank.",
        example = "jfabio") String username,
                             @NotBlank(message = "Password should not be blank") @Schema(
                                     description = "The password for the new user. Must not be blank",
                                     example = "minhasenha123") String password) {}
