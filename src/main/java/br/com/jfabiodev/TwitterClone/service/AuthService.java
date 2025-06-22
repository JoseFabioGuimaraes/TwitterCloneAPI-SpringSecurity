package br.com.jfabiodev.TwitterClone.service;

import br.com.jfabiodev.TwitterClone.dtos.LoginDTO;
import br.com.jfabiodev.TwitterClone.dtos.LoginResponseDTO;
import br.com.jfabiodev.TwitterClone.entities.Role;
import br.com.jfabiodev.TwitterClone.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private  final BCryptPasswordEncoder passwordEncoder;


    public AuthService(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO authenticate(LoginDTO loginDTO){
        var user = userRepository.findByUsername(loginDTO.username());
        if(user.isEmpty() || !user.get().isLoginCorrect(loginDTO, passwordEncoder)){
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
                .expiresAt(Instant.now().plusSeconds(expiresIn))
                .claim("scope",scopes)
                .build();
        var jwtValue =jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResponseDTO(jwtValue, expiresIn);
    }
}
