package org.example.htmldesgin.contorller;

import org.example.htmldesgin.service.LoginService;
import org.example.htmldesgin.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> loginData) {
        String account = loginData.get("account");
        String password = loginData.get("password");
        
        if (account == null || password == null) {
            return Result.error("账号和密码不能为空");
        }
        
        try {
            String token = loginService.login(account, password);
            if (token != null) {
                return Result.success("登录成功", token);
            }
            return Result.error("账号或密码错误");
        } catch (Exception e) {
            return Result.error("登录失败：" + e.getMessage());
        }
    }

    @GetMapping("/user/info")
    public Result getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            // 从 token 中获取用户信息
            String usernameAndRole = loginService.getUsernameAndRoleFromToken(token);
            if (usernameAndRole != null) {
                return Result.success("获取成功", usernameAndRole);
            }
            return Result.error("获取用户信息失败");
        } catch (Exception e) {
            return Result.error("获取用户信息失败：" + e.getMessage());
        }
    }

    @PostMapping("/user/password")
    public Result changePassword(@RequestHeader("Authorization") String token, 
                               @RequestBody Map<String, String> passwordData) {
        try {
            String oldPassword = passwordData.get("oldPassword");
            String newPassword = passwordData.get("newPassword");
            
            if (oldPassword == null || newPassword == null) {
                return Result.error("密码不能为空");
            }

            String username = loginService.getUsernameAndRoleFromToken(token).split(" ")[0];
            boolean success = loginService.changePassword(username, oldPassword, newPassword);
            
            if (success) {
                return Result.success("密码修改成功");
            }
            return Result.error("原密码错误");
        } catch (Exception e) {
            return Result.error("修改密码失败：" + e.getMessage());
        }
    }
}
