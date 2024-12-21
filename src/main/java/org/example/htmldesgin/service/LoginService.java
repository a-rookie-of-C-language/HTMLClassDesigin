package org.example.htmldesgin.service;

import org.example.htmldesgin.dao.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginService {
    @Autowired
    private LoginMapper loginMapper;
    public boolean login(String email, String password) {
        return loginMapper.login(email, password)!=null;
    }
}
