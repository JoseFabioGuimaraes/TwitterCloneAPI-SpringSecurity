package br.com.jfabiodev.TwitterClone.controller;

import br.com.jfabiodev.TwitterClone.dtos.CreatePostDTO;
import br.com.jfabiodev.TwitterClone.dtos.FeedDTO;
import br.com.jfabiodev.TwitterClone.dtos.PostResponseDTO;
import br.com.jfabiodev.TwitterClone.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Tag(
        name = "Posts",
        description = "Endpoints for creating, retrieving, and deleting posts."
)
@RestController
public class PostController {

    private final PostService postService;


    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("/create-post")
    @Operation(summary = "Create new post",
            description = "Creates a new post with the given content. Requires JWT authentication. Returns the created" +
                    " post along with the Location header pointing to the new resource."
    )
    @Transactional
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody CreatePostDTO dto, JwtAuthenticationToken token, UriComponentsBuilder uriBuilder){
        var response = postService.createPost(dto, token.getName());
        URI location = uriBuilder.path("/posts/{id}").buildAndExpand(response.postId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/post/{id}")
    @Operation (summary = "Delete post by ID",
            description = "Deletes a post tih the given ID. Only the post author or an administrator can perform this"+
                    " action. Requires a valid JWT Token"
    )
    @Transactional
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id, JwtAuthenticationToken token){
        postService.deletePost(id, token.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/feed")
    @Operation(summary = "Get public post feed",
            description = "Retrieves a paginated list of recent posts. Requires JWT Token."
    )
    public ResponseEntity<FeedDTO> feed(@Parameter(description = "Page number (starting with 0)", example = "0")
                                            @RequestParam(value = "page",defaultValue = "0") int page,
                                        @Parameter(description = "Number of posts per page", example = "10")
                                        @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        var feed = postService.getFeed(page, pageSize);
        return  ResponseEntity.ok(feed);

    }
}
