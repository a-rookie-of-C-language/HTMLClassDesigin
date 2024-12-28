package org.example.htmldesgin.contorller;

import lombok.extern.slf4j.Slf4j;
import org.example.htmldesgin.service.EmailService;
import org.example.htmldesgin.utils.EmailConfig;
import org.example.htmldesgin.utils.EmailRequest;
import org.example.htmldesgin.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/email")
@Slf4j
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/config")
    public Result updateConfig(@RequestBody EmailConfig config) {
        try {
            emailService.updateEmailConfig(config);
            return Result.success("邮箱配置更新成功");
        } catch (Exception e) {
            log.error("更新邮箱配置失败", e);
            return Result.error("更新邮箱配置失败：" + e.getMessage());
        }
    }

    @PostMapping("/send")
    public Result sendEmail(@RequestBody EmailRequest request) {
        try {
            emailService.sendEmail(request.getTo(), request.getSubject(), request.getContent());
            return Result.success("邮件发送成功");
        } catch (Exception e) {
            log.error("邮件发送失败", e);
            return Result.error("邮件发送失败：" + e.getMessage());
        }
    }

    @PostMapping("/send-with-attachments")
    public Result sendEmailWithAttachments(
            @RequestParam("to") String to,
            @RequestParam("subject") String subject,
            @RequestParam("content") String content,
            @RequestParam(value = "attachments", required = false) MultipartFile[] attachments) {
        try {
            emailService.sendEmailWithAttachments(to, subject, content, attachments);
            return Result.success("邮件发送成功");
        } catch (Exception e) {
            log.error("邮件发送失败", e);
            return Result.error("邮件发送失败：" + e.getMessage());
        }
    }
}
