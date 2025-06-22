package br.com.jfabiodev.TwitterClone.dtos;

import java.util.Set;
import java.util.UUID;

public record UserListResponseDTO(UUID userId,
                                  String username,
                                  Set<String> roles) {
}
