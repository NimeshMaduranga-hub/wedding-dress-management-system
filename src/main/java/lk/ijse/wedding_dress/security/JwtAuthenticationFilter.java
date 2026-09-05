package lk.ijse.wedding_dress.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");

        // No JWT token
        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authHeader.substring(7);

        try {

            System.out.println("JWT HEADER FOUND");

            // Check JWT validity
            if (!jwtUtil.isTokenValid(jwtToken)) {

                System.out.println("JWT TOKEN INVALID");

                filterChain.doFilter(request, response);
                return;
            }

            System.out.println("JWT TOKEN VALID");

            // Get username from JWT
            String username =
                    jwtUtil.extractUsername(jwtToken);

            // Check authentication doesn't already exist
            if (username != null &&
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailsService
                                .loadUserByUsername(username);

                System.out.println(
                        "JWT Username: " + username
                );

                System.out.println(
                        "JWT Authorities: "
                                + userDetails.getAuthorities()
                );

                // Check account is enabled
                if (userDetails.isEnabled()) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);

                    System.out.println(
                            "JWT AUTHENTICATION SET"
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "JWT ERROR: "
                            + e.getClass().getSimpleName()
            );

            System.out.println(
                    "JWT ERROR MESSAGE: "
                            + e.getMessage()
            );

            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}