package br.com.jfabiodev.TwitterClone.controller;


import br.com.jfabiodev.TwitterClone.dtos.LoginDTO;
import br.com.jfabiodev.TwitterClone.dtos.LoginResponseDTO;
import br.com.jfabiodev.TwitterClone.entities.Role;
import br.com.jfabiodev.TwitterClone.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEnconder;

    public TokenController(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEnconder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO){
        var user = userRepository.findByUsername(loginDTO.username());
        if(user.isEmpty() || !user.get().isLoginCorrect(loginDTO, passwordEnconder)){
            throw new BadCredentialsException("User or password is invalid");
        }

        var expiresIn = 300L;
        var scopes = user.get().getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("jfabiodev.twitterclone.com")
                .subject(user.get().getUserID().toString())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3000))
                .claim("scope", scopes)
                .build();
        var jwtValue =jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return ResponseEntity.ok(new LoginResponseDTO(jwtValue, expiresIn));
    }
}
