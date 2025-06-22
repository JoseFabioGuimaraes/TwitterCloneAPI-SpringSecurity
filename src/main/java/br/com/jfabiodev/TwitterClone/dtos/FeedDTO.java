package br.com.jfabiodev.TwitterClone.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FeedDTO(@NotNull @Valid List<FeedItemDTO> feedItems,
                      int page,
                      int pageSize,
                      int totalPages,
                      long totalElements
) {
}
