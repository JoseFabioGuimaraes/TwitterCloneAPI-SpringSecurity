package br.com.jfabiodev.TwitterClone.service;

import br.com.jfabiodev.TwitterClone.dtos.CreateUserDTO;
import br.com.jfabiodev.TwitterClone.entities.Role;
import br.com.jfabiodev.TwitterClone.entities.User;
import br.com.jfabiodev.TwitterClone.repository.RoleRepository;
import br.com.jfabiodev.TwitterClone.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public CreateUserResponseDTO createUser(CreateUserDTO userDTO){
        if (userRepository.findByUsername(userDTO.username()).isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username already exists");
        }
        var user = new User();
        user.setUsername(userDTO.username());
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        user.setRoles(Set.of(roleRepository.findByName(Role.Values.BASIC.name())));
        var saved = userRepository.save(user);

        return new CreateUserResponseDTO(saved.getUserID(),saved.getUsername());
    }

    public List<UserListResponseDTO> listAllUsers() {
        return userRepository.findAll().stream().map(
                user -> new UserListResponseDTO(
                        user.getUserID(),
                        user.getUsername(),
                        user.getRoles().stream()
                                .map(Role::getName)
                                .collect(Collectors.toSet())
                )
        ).toList();
    }
}
