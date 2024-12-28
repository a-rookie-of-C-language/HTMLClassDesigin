package org.example.htmldesgin.utils;

import lombok.Data;

@Data
public class EmailConfig {
    private String host;        // SMTP服务器
    private Integer port;       // SMTP端口
    private String username;    // 发件人邮箱
    private String password;    // 邮箱密码
    private String fromName;    // 发件人名称
}