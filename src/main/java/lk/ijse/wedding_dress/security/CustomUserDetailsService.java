package lk.ijse.wedding_dress.security;

import lk.ijse.wedding_dress.entity.User;
import lk.ijse.wedding_dress.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + username
                        )
                );

        String role = user.getRole();

        /*
         * Fallback to normalized role relationship.
         */
        if (role == null && user.getRoleEntity() != null) {
            role = user.getRoleEntity().getName();
        }

        if (role == null) {
            role = "USER";
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(
                        new SimpleGrantedAuthority(role)
                )
                .disabled(!user.getEnabled())
                .build();
    }
}