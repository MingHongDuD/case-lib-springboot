package com.damon.controller;

import com.damon.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 认证控制器，提供登录接口生成 JWT 令牌
 *
 * @author damon du/minghongdud
 */
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /**
     * 构造函数，注入认证管理器和 JWT 工具类
     *
     * @param authenticationManager 认证管理器
     * @param jwtUtil               JWT 工具类
     */
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 登录接口，验证用户凭证并返回 JWT 令牌
     *
     * @param loginRequest 包含用户名和密码的请求体
     * @return JWT 令牌
     */
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        String token = jwtUtil.generateToken(authentication.getPrincipal());
        return ResponseEntity.ok(Map.of("token", token));
    }
}