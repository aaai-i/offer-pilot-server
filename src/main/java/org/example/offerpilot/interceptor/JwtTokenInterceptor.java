package org.example.offerpilot.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.offerpilot.common.ResultCode;
import org.example.offerpilot.common.UserContext;
import org.example.offerpilot.constant.JwtClaimsConstant;
import org.example.offerpilot.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-name}")
    private String tokenName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(tokenName);
        if (token == null || token.isBlank()) {
            response.setStatus(ResultCode.UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"未登录或token为空\",\"data\":null}");
            return false;
        }

        try {
            Claims claims = JwtUtil.parseJWT(secretKey, token);
            Object userId = claims.get(JwtClaimsConstant.USER_ID);
            UserContext.setUserId(Long.valueOf(userId.toString()));
            return true;
        } catch (Exception e) {
            log.error("JWT解析失败", e);
            response.setStatus(ResultCode.UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"token无效或已过期\",\"data\":null}");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }
}
