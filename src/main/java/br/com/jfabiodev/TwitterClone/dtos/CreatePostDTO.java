package br.com.jfabiodev.TwitterClone.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreatePostDTO(
        @NotBlank(message = "Content should not be blank")
        @Schema(description = "Content of the post. Cannot be blank.",
                example ="Hello world :) This is my first post") String content
) {
}
