package org.example.htmldesgin.service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.example.htmldesgin.utils.EmailConfig;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Properties;

@Service
@Slf4j
public class EmailService {

    private JavaMailSender mailSender;
    private String fromName;
    private String username;

    public void sendEmail(String to, String subject, String content) {
        if (mailSender == null) {
            throw new RuntimeException("请先配置邮箱信息");
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");  // 指定UTF-8编码

            // 设置发件人（带名称）
            helper.setFrom(new InternetAddress(username, fromName, "UTF-8"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);  // 第二个参数true表示支持HTML内容

            log.info("开始发送邮件到: {}", to);
            mailSender.send(message);
            log.info("邮件发送成功");
        } catch (Exception e) {
            log.error("邮件发送失败: {}", e.getMessage(), e);
            throw new RuntimeException("邮件发送失败: " + e.getMessage());
        }
    }

    public void updateEmailConfig(EmailConfig config) {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(config.getHost());
        mailSenderImpl.setPort(config.getPort());
        mailSenderImpl.setUsername(config.getUsername());
        mailSenderImpl.setPassword(config.getPassword());

        // 配置邮件属性
        Properties props = getProperties(config);

        mailSenderImpl.setJavaMailProperties(props);

        this.mailSender = mailSenderImpl;
        this.fromName = config.getFromName();
        this.username = config.getUsername();

        log.info("邮箱配置已更新: {}", config.getUsername());
    }

    private static Properties getProperties(EmailConfig config) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.port", config.getPort());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "3000");
        props.put("mail.smtp.writetimeout", "5000");
        // 添加编码设置
        props.put("mail.smtp.encoding", "UTF-8");
        props.put("mail.mime.charset", "UTF-8");
        return props;
    }

    public void sendEmailWithAttachments(String to, String subject, String content, MultipartFile[] attachments) {
        if (mailSender == null) {
            throw new RuntimeException("请先配置邮箱信息");
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(new InternetAddress(username, fromName, "UTF-8"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            // 处理附件
            if (attachments != null) {
                for (MultipartFile file : attachments) {
                    if (file != null && !file.isEmpty()) {
                        // 添加附件
                        helper.addAttachment(file.getOriginalFilename(), file);
                        log.info("添加附件: {}", file.getOriginalFilename());
                    }
                }
            }

            mailSender.send(message);
        } catch (Exception e) {
            log.error("邮件发送失败: {}", e.getMessage(), e);
            throw new RuntimeException("邮件发送失败: " + e.getMessage());
        }
    }
}
