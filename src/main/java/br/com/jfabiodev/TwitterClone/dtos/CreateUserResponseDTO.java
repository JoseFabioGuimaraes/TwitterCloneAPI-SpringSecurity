package br.com.jfabiodev.TwitterClone.dtos;

import java.util.UUID;

public record CreateUserResponseDTO(UUID userId,
                                    String username) {
}
