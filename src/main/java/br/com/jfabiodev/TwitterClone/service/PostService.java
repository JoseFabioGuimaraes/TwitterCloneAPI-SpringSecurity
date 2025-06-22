package br.com.jfabiodev.TwitterClone.service;

import br.com.jfabiodev.TwitterClone.dtos.CreatePostDTO;
import br.com.jfabiodev.TwitterClone.dtos.FeedDTO;
import br.com.jfabiodev.TwitterClone.dtos.FeedItemDTO;
import br.com.jfabiodev.TwitterClone.dtos.PostResponseDTO;
import br.com.jfabiodev.TwitterClone.entities.Post;
import br.com.jfabiodev.TwitterClone.entities.Role;
import br.com.jfabiodev.TwitterClone.repository.PostRepository;
import br.com.jfabiodev.TwitterClone.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PostResponseDTO createPost(CreatePostDTO dto, String userId){
        var user = userRepository.findById(UUID.fromString(userId)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        var post = new Post();
        post.setContent(dto.content());
        post.setUser(user);
        var saved = postRepository.save(post);

        return new PostResponseDTO(saved.getPostID(), saved.getContent(),saved.getUser().getUsername(),
                saved.getCreationTimestamp().toString());
    }

    public FeedDTO getFeed(int page, int pageSize){
        var postsPage = postRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.DESC,
                "creationTimestamp")).map(post-> new FeedItemDTO(post.getPostID(),
                post.getContent(), post.getUser().getUsername()));

        return new FeedDTO(postsPage.getContent(), page, pageSize, postsPage.getTotalPages(),postsPage.getTotalPages());
    }

    @Transactional
    public void deletePost(Long postId, String userId){
        var user = userRepository.findById(UUID.fromString(userId)).orElseThrow(()->
                new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not Found"));
        var post = postRepository.findById(postId).orElseThrow(()-> new ResponseStatusException(HttpStatus.FORBIDDEN,
                "Post not found"));

        var isAdmin = user.getRoles().stream().anyMatch(
                role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name())
        );
        if(post.getUser().getUserID().equals(user.getUserID())|| isAdmin){
            postRepository.deleteById(postId);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Not allowed to delete this post");
        }
    }
}
