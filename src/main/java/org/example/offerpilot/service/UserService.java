package org.example.offerpilot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.offerpilot.entity.SysUser;
import org.example.offerpilot.vo.UserInfoVO;

public interface UserService extends IService<SysUser> {
    UserInfoVO getCurrentUserInfo(Long userId);
    void updatePassword(Long userId, String oldPassword, String newPassword);
}