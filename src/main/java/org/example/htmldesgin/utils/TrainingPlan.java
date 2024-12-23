package org.example.htmldesgin.utils;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TrainingPlan {
    private Long id;
    private String projectNo;
    private String companyName;
    private String groupName;
    private String system;
    private String trainer;
    private String trainingTime;
    private String trainingContext;
    private LocalDate trainingDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}