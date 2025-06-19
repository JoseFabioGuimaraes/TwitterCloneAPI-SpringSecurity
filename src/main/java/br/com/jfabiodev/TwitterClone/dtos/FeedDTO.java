package br.com.jfabiodev.TwitterClone.dtos;

import java.util.List;

public record FeedDTO(List<FeedItemDTO> feedItems,
                      int page,
                      int pageSize,
                      int totalPages,
                      long totalElements
) {
}
