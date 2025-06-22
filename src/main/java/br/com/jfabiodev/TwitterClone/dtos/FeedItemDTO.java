package br.com.jfabiodev.TwitterClone.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FeedItemDTO(@NotNull (message = "Post ID is Required") Long postId,
                          @NotBlank(message = "Content should not be blank") String content,
                          @NotBlank(message = "Username should not be blank") String username) {
}
