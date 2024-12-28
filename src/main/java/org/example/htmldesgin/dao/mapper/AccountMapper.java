package org.example.htmldesgin.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.htmldesgin.utils.AccountUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface AccountMapper {
    List<AccountUser> findByPage(@Param("offset") Integer offset, @Param("limit") Integer limit,
                                 @Param("username") String username,
                                 @Param("telephone") String telephone,
                                 @Param("email") String email);

    Integer getTotal(@Param("username") String username,
                     @Param("telephone") String telephone,
                     @Param("email") String email);

    Integer addAccount(AccountUser accountUser);

    Integer updateAccount(AccountUser accountUser);

    Integer deleteAccount(Integer[] ids);

    List<String> findUsernameByIds(Integer[] id);
}
