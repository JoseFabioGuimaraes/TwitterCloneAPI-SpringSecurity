package br.com.jfabiodev.TwitterClone.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO (@NotBlank(message = "Username should not be blank") String username,
                        @NotBlank(message = "Password should not be blank") String password) {
}
