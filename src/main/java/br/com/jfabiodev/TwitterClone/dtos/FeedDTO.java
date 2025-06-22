package br.com.jfabiodev.TwitterClone.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FeedDTO(@NotNull @Valid @Schema(description = "List of feed items") List<FeedItemDTO> feedItems,
                      @Schema(description = "Curret page number", example = "0") int page,
                      @Schema(description = "Number of items per page", example = "10") int pageSize,
                      @Schema(description = "Total number of pages", example = "5") int totalPages,
                      @Schema(description = "Total of feed items", example = "50") long totalElements
) {
}
