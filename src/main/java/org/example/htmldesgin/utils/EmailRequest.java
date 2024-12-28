package org.example.htmldesgin.utils;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EmailRequest {
    private String to;
    private String subject;
    private String content;
    private MultipartFile[] attachments;
}
