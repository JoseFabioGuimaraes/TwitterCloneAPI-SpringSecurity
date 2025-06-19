package br.com.jfabiodev.TwitterClone.dtos;

public record FeedItemDTO(Long postID,
                          String content,
                          String username) {
}
