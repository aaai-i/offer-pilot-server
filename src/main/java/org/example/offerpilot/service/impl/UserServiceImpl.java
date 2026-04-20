package org.example.offerpilot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.offerpilot.entity.SysUser;
import org.example.offerpilot.exception.BizException;
import org.example.offerpilot.mapper.SysUserMapper;
import org.example.offerpilot.service.UserService;
import org.example.offerpilot.utils.PasswordUtil;
import org.example.offerpilot.vo.UserInfoVO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserService {

    private final SysUserMapper sysUserMapper;

    @Override
    public UserInfoVO getCurrentUserInfo(Long userId) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new BizException("用户不存在");
        }

        UserInfoVO vo = new UserInfoVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        vo.setEmail(user.getEmail());
        vo.setStatus(user.getStatus());
        return vo;
    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new BizException("用户不存在");
        }

        if (!PasswordUtil.matches(oldPassword, user.getPassword())) {
            throw new BizException("旧密码错误");
        }

        user.setPassword(PasswordUtil.encrypt(newPassword));
        sysUserMapper.updateById(user);
    }
}