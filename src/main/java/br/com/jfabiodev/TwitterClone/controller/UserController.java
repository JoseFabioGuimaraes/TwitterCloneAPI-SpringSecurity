package br.com.jfabiodev.TwitterClone.controller;

import br.com.jfabiodev.TwitterClone.dtos.CreateUserDTO;
import br.com.jfabiodev.TwitterClone.service.CreateUserResponseDTO;
import br.com.jfabiodev.TwitterClone.service.UserListResponseDTO;
import br.com.jfabiodev.TwitterClone.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    public UserController (UserService userService){
        this.userService = userService;
    }


    @Transactional
    @PostMapping("/user/create")
    public ResponseEntity<CreateUserResponseDTO> createUser (@RequestBody CreateUserDTO createUserDTO, UriComponentsBuilder uriBuilder){
        var response =  userService.createUser(createUserDTO);
        URI location = uriBuilder.path("/user/{id}").buildAndExpand(response.userID()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/all-users")
    public ResponseEntity<List<UserListResponseDTO>> listUsers(){
       return ResponseEntity.ok(userService.listAllUsers());
    }
}
