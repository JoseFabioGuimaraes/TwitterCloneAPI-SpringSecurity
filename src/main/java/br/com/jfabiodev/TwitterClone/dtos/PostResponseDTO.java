package br.com.jfabiodev.TwitterClone.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record PostResponseDTO(@Schema(description = "Unique identifier of post", example = "101") Long postId,
                              @Schema(description = "Content of the post", example = "Hello world :) This is my first post") String content,
                              @Schema(description = "Username of the post author", example = "admin") String username,
                              @Schema(description = "Creation timestamp of the post in ISO format",
                              example = "2025-06-22T15:30:00Z") String createdAt) {}
