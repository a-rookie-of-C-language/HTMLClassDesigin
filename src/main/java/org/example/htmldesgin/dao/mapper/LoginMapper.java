package org.example.htmldesgin.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.htmldesgin.utils.AccountUser;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface LoginMapper {
    @Select("SELECT * FROM AccountUser WHERE email = #{email} AND password = #{password}")
    AccountUser loginByEmail(String email, String password);

    @Select("SELECT * FROM AccountUser WHERE telephone = #{phone} AND password = #{password}")
    AccountUser loginByPhone(String phone, String password);

    @Select("SELECT * FROM AccountUser WHERE username = #{username}")
    AccountUser getUserByUsername(@Param("username") String username);

    @Update("UPDATE AccountUser SET password = #{newPassword} WHERE username = #{username}")
    int updatePassword(@Param("username") String username, @Param("newPassword") String newPassword);
}
