package lk.ijse.wedding_dress.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
}