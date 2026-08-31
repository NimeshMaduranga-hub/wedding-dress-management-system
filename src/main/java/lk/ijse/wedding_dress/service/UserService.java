package lk.ijse.wedding_dress.service;

import lk.ijse.wedding_dress.dto.LoginRequestDTO;
import lk.ijse.wedding_dress.dto.LoginResponseDTO;
import lk.ijse.wedding_dress.dto.RegisterDTO;

public interface UserService {

    void register(RegisterDTO registerDTO);

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}