package com.damon.util;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类，负责生成、解析和验证 JWT 令牌
 *
 * @author damon du/minghongdud
 */
@Component
public class JwtUtil {

    // JWT 签名密钥，生产环境中应存储在环境变量或配置文件中
    private static final String SECRET_KEY = "your-256-bit-secret-key-1234567890abcdef";
    // 令牌有效期（毫秒），这里设置为1小时
    private static final long EXPIRATION_TIME = 3600_000;

    /**
     * 生成 JWT 令牌
     *
     * @param userDetails 用户详情
     * @return JWT 令牌
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName()), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 从 JWT 令牌中提取用户名
     *
     * @param token JWT 令牌
     * @return 用户名
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * 验证 JWT 令牌是否有效
     *
     * @param token       JWT 令牌
     * @param userDetails 用户详情
     * @return 是否有效
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return username.equals(userDetails.getUsername()) && !claims.getExpiration().before(new Date());
    }
}
