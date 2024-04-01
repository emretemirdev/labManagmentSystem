package com.emretemir.laboratorymanagementsystem.dto.Notification;

import java.util.Date;

public record NotificationDTO(
        Long id,
        Date createdAt,
        String notificationType,
        Long reportId,
        String message,
        Long laborantId,
        String laborantName,
        String reportName
) {}