package org.example.offerpilot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.example.offerpilot.constant.JwtClaimsConstant;
import org.example.offerpilot.dto.LoginDTO;
import org.example.offerpilot.dto.RegisterDTO;
import org.example.offerpilot.entity.SysUser;
import org.example.offerpilot.exception.BizException;
import org.example.offerpilot.mapper.SysUserMapper;
import org.example.offerpilot.service.AuthService;
import org.example.offerpilot.utils.JwtUtil;
import org.example.offerpilot.utils.PasswordUtil;
import org.example.offerpilot.vo.LoginVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserMapper sysUserMapper;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.ttl}")
    private Long ttl;

    @Override
    public void register(RegisterDTO dto) {
        SysUser exist = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, dto.getUsername()));
        if (exist != null) {
            throw new BizException("用户名已存在");
        }

        SysUser user = new SysUser();
        user.setUsername(dto.getUsername());
        user.setPassword(PasswordUtil.encrypt(dto.getPassword()));
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setStatus(1);

        sysUserMapper.insert(user);
    }

    @Override
    public LoginVO login(LoginDTO dto) {
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, dto.getUsername()));

        if (user == null) {
            throw new BizException("用户名或密码错误");
        }

        if (!PasswordUtil.matches(dto.getPassword(), user.getPassword())) {
            throw new BizException("用户名或密码错误");
        }

        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BizException("当前用户已被禁用");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        claims.put(JwtClaimsConstant.USERNAME, user.getUsername());

        String token = JwtUtil.createJWT(secretKey, ttl, claims);
        return new LoginVO(user.getId(), user.getUsername(), token);
    }
}
