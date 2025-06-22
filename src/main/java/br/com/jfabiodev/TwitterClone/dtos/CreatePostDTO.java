package br.com.jfabiodev.TwitterClone.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreatePostDTO(@NotBlank(message = "Content should not be blank") String content) {
}
