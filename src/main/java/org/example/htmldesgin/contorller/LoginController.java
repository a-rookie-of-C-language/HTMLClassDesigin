package org.example.htmldesgin.contorller;

import org.example.htmldesgin.service.LoginService;
import org.example.htmldesgin.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    public Result login(String email, String password) {
        return loginService.login(email, password)
                ? Result.success()
                : Result.error();
    }
}
