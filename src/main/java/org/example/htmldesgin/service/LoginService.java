package org.example.htmldesgin.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.htmldesgin.dao.mapper.LoginMapper;
import org.example.htmldesgin.utils.AccountUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.regex.Pattern;

@Service
public class LoginService {
    @Autowired
    private LoginMapper loginMapper;

    public static final String JWT_KEY = "your_secret_key_at_least_32_characters_long";
    private static final long EXPIRATION_TIME = 86400000; // 24小时

    public String login(String account, String password) {
        AccountUser user;
        // 判断是邮箱还是手机号
        if (isEmail(account)) {
            user = loginMapper.loginByEmail(account, password);
        } else if (isPhone(account)) {
            user = loginMapper.loginByPhone(account, password);
        } else {
            throw new IllegalArgumentException("账号格式不正确");
        }

        if (user != null) {
            return generateToken(user);
        }
        return null;
    }

    // 从token中获取用户名
    public String getUsernameAndRoleFromToken(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(JWT_KEY.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject() +" "+claims.get("role");
        } catch (Exception e) {
            return null;
        }
    }

    // 修改密码
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        // 先验证旧密码是否正确
        AccountUser user = loginMapper.getUserByUsername(username);
        if (user == null) {
            return false;
        }

        // 验证旧密码
        if (!oldPassword.equals(user.getPassword())) {
            return false;
        }

        // 更新新密码
        return loginMapper.updatePassword(username, newPassword) > 0;
    }

    private String generateToken(AccountUser user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(JWT_KEY.getBytes()))
                .compact();
    }

    private boolean isEmail(String account) {
        return Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", account);
    }

    private boolean isPhone(String account) {
        return Pattern.matches("^1[3-9]\\d{9}$", account);
    }
}
