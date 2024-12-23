package org.example.htmldesgin.utils;

import lombok.Data;

@Data
public class Project {
    private String projectNo;

    private String companyName;

    private String groupName;

    private String system;

    private String contactPerson;

    private String contactEmail;

    private Double hourlyRate;

    private Double contractHours;

    private Double promisedHours;

    private Double consumedHours;
}