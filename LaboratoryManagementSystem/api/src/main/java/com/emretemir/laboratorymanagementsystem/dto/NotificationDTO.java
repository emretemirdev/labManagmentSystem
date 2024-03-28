package com.emretemir.laboratorymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NotificationDTO {
    private Long id;
    private Date createdAt;
    private String notificationType;
    private Long reportId;
    private String message;
    private Long laborantId;
    private String laborantName;
    private String reportName;

}

