package br.com.jfabiodev.TwitterClone.controller;

import br.com.jfabiodev.TwitterClone.dtos.CreatePostDTO;
import br.com.jfabiodev.TwitterClone.dtos.FeedDTO;
import br.com.jfabiodev.TwitterClone.dtos.FeedItemDTO;
import br.com.jfabiodev.TwitterClone.entities.Post;
import br.com.jfabiodev.TwitterClone.entities.Role;
import br.com.jfabiodev.TwitterClone.repository.PostRepository;
import br.com.jfabiodev.TwitterClone.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/create-post")
    public ResponseEntity<Void> createPost(@RequestBody CreatePostDTO dto, JwtAuthenticationToken token){
        var user = userRepository.findById(UUID.fromString(token.getName()));
        var post = new Post();
        post.setUser(user.get());
        post.setContent(dto.content());
        postRepository.save(post);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Void> deleteTwetter(@PathVariable("id") Long id, JwtAuthenticationToken token){

        var user = userRepository.findById(UUID.fromString(token.getName()));
        var post = postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var isAdmin = user.get().getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        if(post.getUser().getUserID().equals(UUID.fromString(token.getName())) || isAdmin){
            postRepository.deleteById(id);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/feed")
    public ResponseEntity<FeedDTO> feed(@RequestParam(value = "page",defaultValue = "0") int page,
                                        @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        var postsFeed = postRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimestamp"))
                .map(post -> new FeedItemDTO(post.getPostID(),post.getContent(),post.getUser().getUsername()));
        return ResponseEntity.ok(new FeedDTO(
                postsFeed.getContent(),page, pageSize ,postsFeed.getTotalPages(),postsFeed.getTotalPages()));

    }
}
