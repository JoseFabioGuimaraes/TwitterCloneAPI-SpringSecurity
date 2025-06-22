package br.com.jfabiodev.TwitterClone.service;

import java.util.UUID;

public record CreateUserResponseDTO(UUID userId,
                                    String username) {
}
