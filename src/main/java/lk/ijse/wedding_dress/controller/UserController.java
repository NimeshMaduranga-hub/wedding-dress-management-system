package lk.ijse.wedding_dress.controller;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/test")
    public String test(Authentication authentication) {

        return "JWT Authentication Successful! Logged in user: "
                + authentication.getName();
    }
}

