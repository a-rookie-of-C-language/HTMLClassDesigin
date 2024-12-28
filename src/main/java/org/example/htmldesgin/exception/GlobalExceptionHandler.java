package org.example.htmldesgin.exception;

import org.example.htmldesgin.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class,DuplicateKeyException.class})
    public Result handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        log.error("数据库唯一约束异常：", e);
        String message = e.getMessage();
        
        // 从错误信息中提取重复的值
        String duplicateValue = "";
        if (message.startsWith("Duplicate entry")) {
            duplicateValue = message.split("'")[1]; // 提取单引号中的值
        }

        // 检查是否是电话号码
        if (PHONE_PATTERN.matcher(duplicateValue).matches()) {
            return Result.error("该电话号码已存在");
        } 
        // 检查是否是邮箱
        else if (duplicateValue.contains("@")) {
            return Result.error("该邮箱已存在");
        }
        else if (message.contains("username")) {
            return Result.error("该用户名已存在");
        }
        
        return Result.error("添加失败：数据已存在");
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("系统异常：", e);
        return Result.error("系统异常，请联系管理员");
    }
} 