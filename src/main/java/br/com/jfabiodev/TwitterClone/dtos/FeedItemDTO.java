package br.com.jfabiodev.TwitterClone.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FeedItemDTO(@NotNull(message = "Post ID is Required") @Schema(description = "Unique identifier of the post",
        example = "2") Long postId,
                          @NotBlank(message = "Content should not be blank") @Schema(description = "Content of the post",
                                  example = "Hello, world!") String content,
                          @NotBlank(message = "Username should not be blank") @Schema(description = "Username of the post author",
                          example = "admin") String username) {
}
