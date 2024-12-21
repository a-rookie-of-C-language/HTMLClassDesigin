package org.example.htmldesgin.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.htmldesgin.utils.User;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface LoginMapper {
    User login(String email, String password);
}
