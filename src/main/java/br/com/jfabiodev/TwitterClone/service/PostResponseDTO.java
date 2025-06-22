package br.com.jfabiodev.TwitterClone.service;

public record PostResponseDTO(Long postId,
                             String content,
                             String username,
                             String createdAt) {}
