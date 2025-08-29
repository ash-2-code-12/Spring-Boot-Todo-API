package dev.ash.todoapp.controller;

import dev.ash.todoapp.model.AppUser;
import dev.ash.todoapp.repository.AppUserRepository;
import dev.ash.todoapp.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")

public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody AppUser user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists with this Email");
        }
        AppUser newUser = AppUser.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role("ROLE_USER")
                .build();

        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AppUser user) {
        try{
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            AppUser newUser = (AppUser) auth.getPrincipal();
            String token = jwtUtil.generateToken(newUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed. Invalid credentials.");
        }
    }
}

