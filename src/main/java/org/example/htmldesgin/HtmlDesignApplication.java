package org.example.htmldesgin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.htmldesgin.dao.mapper")
public class HtmlDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(HtmlDesignApplication.class, args);
    }

}
