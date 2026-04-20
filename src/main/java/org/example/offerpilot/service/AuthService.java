package org.example.offerpilot.service;

import org.example.offerpilot.dto.LoginDTO;
import org.example.offerpilot.dto.RegisterDTO;
import org.example.offerpilot.vo.LoginVO;

public interface AuthService {
    void register(RegisterDTO dto);
    LoginVO login(LoginDTO dto);
}