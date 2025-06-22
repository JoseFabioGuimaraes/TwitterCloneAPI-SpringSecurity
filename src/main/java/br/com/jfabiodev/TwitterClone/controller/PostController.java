package br.com.jfabiodev.TwitterClone.controller;

import br.com.jfabiodev.TwitterClone.dtos.CreatePostDTO;
import br.com.jfabiodev.TwitterClone.dtos.FeedDTO;
import br.com.jfabiodev.TwitterClone.service.PostResponseDTO;
import br.com.jfabiodev.TwitterClone.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class PostController {

    private final PostService postService;


    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("/create-post")
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody CreatePostDTO dto, JwtAuthenticationToken token, UriComponentsBuilder uriBuilder){
        var response = postService.createPost(dto, token.getName());
        URI location = uriBuilder.path("/posts/{id}").buildAndExpand(response.postId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id, JwtAuthenticationToken token){
        postService.deletePost(id, token.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/feed")
    public ResponseEntity<FeedDTO> feed(@RequestParam(value = "page",defaultValue = "0") int page,
                                        @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        var feed = postService.getFeed(page, pageSize);
        return  ResponseEntity.ok(feed);

    }
}
