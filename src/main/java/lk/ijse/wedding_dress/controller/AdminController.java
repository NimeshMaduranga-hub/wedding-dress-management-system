package lk.ijse.wedding_dress.controller;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/test")
    public String test(Authentication authentication) {

        return "ADMIN Authentication Successful! Logged in user: "
                + authentication.getName();
    }
}

