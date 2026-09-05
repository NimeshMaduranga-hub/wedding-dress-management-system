package lk.ijse.wedding_dress.controller;

import lk.ijse.wedding_dress.dto.LoginRequest;
import lk.ijse.wedding_dress.dto.LoginResponseDTO;
import lk.ijse.wedding_dress.dto.RegisterRequest;
import lk.ijse.wedding_dress.entity.User;
import lk.ijse.wedding_dress.repository.UserRepository;
import lk.ijse.wedding_dress.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // =========================
    // LOGIN
    // =========================

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequest request) {

        logger.info("Login attempt for user: {}", request.getUsername());

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        String username = authentication.getName();

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(authority -> authority.getAuthority())
                .orElse("USER");

        String token = jwtUtil.generateToken(
                username,
                role
        );

        logger.info("User logged in successfully: {}", username);

        return ResponseEntity.ok(
                new LoginResponseDTO(
                        token,
                        username,
                        role
                )
        );
    }


    // =========================
    // REGISTER
    // =========================

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {

        // Check username
        if (userRepository
                .findByUsername(request.getUsername())
                .isPresent()) {

            return ResponseEntity
                    .badRequest()
                    .body("Username already exists!");
        }

        // Create user
        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // Encrypt password
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        // Default role
        user.setRole("USER");

        // Enable account
        user.setEnabled(true);

        // Save database
        userRepository.save(user);

        return ResponseEntity.ok(
                "User registered successfully!"
        );
    }
}