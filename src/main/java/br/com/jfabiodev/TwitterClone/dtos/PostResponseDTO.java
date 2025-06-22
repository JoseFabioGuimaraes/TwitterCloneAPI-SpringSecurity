package br.com.jfabiodev.TwitterClone.dtos;

public record PostResponseDTO(Long postId,
                             String content,
                             String username,
                             String createdAt) {}
