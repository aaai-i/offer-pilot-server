package org.example.offerpilot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.offerpilot.common.Result;
import org.example.offerpilot.dto.LoginDTO;
import org.example.offerpilot.dto.RegisterDTO;
import org.example.offerpilot.service.AuthService;
import org.example.offerpilot.vo.LoginVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public Result<Void> register(@RequestBody @Valid RegisterDTO dto) {
        authService.register(dto);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody @Valid LoginDTO dto) {
        return Result.success(authService.login(dto));
    }
}