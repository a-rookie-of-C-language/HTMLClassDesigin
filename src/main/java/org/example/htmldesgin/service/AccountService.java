package org.example.htmldesgin.service;

import lombok.extern.slf4j.Slf4j;
import org.example.htmldesgin.dao.mapper.AccountMapper;
import org.example.htmldesgin.utils.AccountUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;

    private static final int PAGE_SIZE = 1000;  // 每次加载1000条

    public Map<String, Object> findByPage(Integer page, Map<String, String> searchParams) {
        log.info("Finding page: {}, size: {}, params: {}", page, PAGE_SIZE, searchParams);
        
        int offset = (page - 1) * PAGE_SIZE;
        String username = searchParams.get("username");
        String telephone = searchParams.get("telephone");
        String email = searchParams.get("email");
        
        List<AccountUser> data = accountMapper.findByPage(offset, PAGE_SIZE, username, telephone, email);
        Integer total = accountMapper.getTotal(username, telephone, email);
        
        Map<String, Object> result = new HashMap<>();
        result.put("records", data);
        result.put("total", total);
        result.put("hasMore", offset + data.size() < total);
        
        return result;
    }

    public Integer addAccount(AccountUser accountUser) {
        try {
            return accountMapper.addAccount(accountUser);
        } catch (DuplicateKeyException e) {
            log.error("添加用户失败，数据重复：", e);
            throw e;
        } catch (Exception e) {
            log.error("添加用户失败：", e);
            throw e;
        }
    }

    public Integer updateAccount(AccountUser accountUser) {
        return accountMapper.updateAccount(accountUser);
    }

    public Integer deleteAccount(Integer[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        return accountMapper.deleteAccount(ids);
    }
}
