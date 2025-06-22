package br.com.jfabiodev.TwitterClone.controller;


import br.com.jfabiodev.TwitterClone.dtos.LoginDTO;
import br.com.jfabiodev.TwitterClone.dtos.LoginResponseDTO;
import br.com.jfabiodev.TwitterClone.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Authentication",
        description = "Endpoints related to user authentication and JWT token generation."
)
@RestController
public class TokenController {

    private final AuthService authService;
    public TokenController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and return JWT Token",
            description = "Validates the provided credentials and returns a JWT Token if authentication is successful. " +
                    "This token must be used for accessing protected endpoints.")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO){
       var response = authService.authenticate(loginDTO);
       return ResponseEntity.ok(response);
    }
}
