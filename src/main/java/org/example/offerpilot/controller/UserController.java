package org.example.offerpilot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.offerpilot.common.Result;
import org.example.offerpilot.common.UserContext;
import org.example.offerpilot.dto.UpdatePasswordDTO;
import org.example.offerpilot.service.UserService;
import org.example.offerpilot.vo.UserInfoVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public Result<UserInfoVO> userInfo() {
        Long userId = UserContext.getUserId();
        return Result.success(userService.getCurrentUserInfo(userId));
    }

    @PutMapping("/password")
    public Result<Void> updatePassword(@RequestBody @Valid UpdatePasswordDTO dto) {
        Long userId = UserContext.getUserId();
        userService.updatePassword(userId, dto.getOldPassword(), dto.getNewPassword());
        return Result.success();
    }
}