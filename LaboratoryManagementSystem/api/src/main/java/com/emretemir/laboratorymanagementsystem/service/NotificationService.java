package com.emretemir.laboratorymanagementsystem.service;


import com.emretemir.laboratorymanagementsystem.dto.NotificationDTO;
import com.emretemir.laboratorymanagementsystem.model.Notification;
import com.emretemir.laboratorymanagementsystem.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationDTO createNotification(
            String notificationType,
            Long reportId,
            String message,
            Long laborantId,
            String laborantName,
            String reportName) {
        Notification notification = new Notification();
        notification.setCreatedAt(new Date());
        notification.setNotificationType(notificationType);
        notification.setReportId(reportId);
        notification.setMessage(message);
        notification.setLaborantId(laborantId);
        notification.setLaborantName(laborantName);
        notification.setReportName(reportName);
        Notification savedNotification = notificationRepository.save(notification);

        return convertToNotificationDTO(savedNotification);
    }

    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(this::convertToNotificationDTO)
                .collect(Collectors.toList());
    }

    private NotificationDTO convertToNotificationDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setNotificationType(notification.getNotificationType());
        dto.setReportId(notification.getReportId());
        dto.setMessage(notification.getMessage());
        dto.setLaborantId(notification.getLaborantId());
        dto.setLaborantName(notification.getLaborantName());
        return dto;
    }
}

