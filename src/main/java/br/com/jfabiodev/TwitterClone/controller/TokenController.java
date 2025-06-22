package br.com.jfabiodev.TwitterClone.controller;


import br.com.jfabiodev.TwitterClone.dtos.LoginDTO;
import br.com.jfabiodev.TwitterClone.dtos.LoginResponseDTO;
import br.com.jfabiodev.TwitterClone.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final AuthService authService;
    public TokenController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO){
       var response = authService.authenticate(loginDTO);
       return ResponseEntity.ok(response);
    }
}
