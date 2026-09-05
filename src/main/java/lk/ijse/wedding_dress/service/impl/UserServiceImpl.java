package lk.ijse.wedding_dress.service.impl;

import lk.ijse.wedding_dress.dto.LoginRequestDTO;
import lk.ijse.wedding_dress.dto.LoginResponseDTO;
import lk.ijse.wedding_dress.dto.RegisterDTO;
import lk.ijse.wedding_dress.entity.User;
import lk.ijse.wedding_dress.repository.UserRepository;
import lk.ijse.wedding_dress.security.JwtUtil;
import lk.ijse.wedding_dress.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void register(RegisterDTO registerDTO) {

        logger.info(
                "Registering new user: {}",
                registerDTO.getUsername()
        );

        // Check username already exists
        if (userRepository
                .findByUsername(registerDTO.getUsername())
                .isPresent()) {

            logger.warn(
                    "Registration failed. Username already exists: {}",
                    registerDTO.getUsername()
            );

            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(registerDTO.getUsername());

        // Save email
        user.setEmail(registerDTO.getEmail());

        // Encrypt password using BCrypt
        user.setPassword(
                passwordEncoder.encode(registerDTO.getPassword())
        );

        // Default role
        user.setRole("ROLE_USER");

        // Enable user account
        user.setEnabled(true);

        userRepository.save(user);

        logger.info(
                "User registered successfully: {}",
                user.getUsername()
        );
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        logger.info(
                "Login attempt for user: {}",
                loginRequestDTO.getUsername()
        );

        // Find user by username
        User user = userRepository
                .findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> {

                    logger.warn(
                            "Login failed. User not found: {}",
                            loginRequestDTO.getUsername()
                    );

                    return new RuntimeException(
                            "Invalid username or password"
                    );
                });

        // Check password
        if (!passwordEncoder.matches(
                loginRequestDTO.getPassword(),
                user.getPassword())) {

            logger.warn(
                    "Login failed. Invalid password for user: {}",
                    loginRequestDTO.getUsername()
            );

            throw new RuntimeException(
                    "Invalid username or password"
            );
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole()
        );

        logger.info(
                "User logged in successfully: {}",
                user.getUsername()
        );

        return new LoginResponseDTO(
                token,
                user.getUsername(),
                user.getRole()
        );
    }
}
