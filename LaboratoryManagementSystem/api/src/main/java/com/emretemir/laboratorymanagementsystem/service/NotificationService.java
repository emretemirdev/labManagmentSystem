package com.emretemir.laboratorymanagementsystem.service;


import com.emretemir.laboratorymanagementsystem.dto.Notification.NotificationDTO;
import com.emretemir.laboratorymanagementsystem.model.Notification;
import com.emretemir.laboratorymanagementsystem.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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


    public void deleteAllNotifications() {
        try {
            notificationRepository.deleteAll();
        } catch (Exception e) {
            throw new ServiceException("Silerken hata oluştu", e);
        }
    }

    private NotificationDTO convertToNotificationDTO(Notification notification) {

        return new NotificationDTO(
                notification.getId(),
                notification.getCreatedAt(),
                notification.getNotificationType(),
                notification.getReportId(),
                notification.getMessage(),
                notification.getLaborantId(),
                notification.getLaborantName(),
                notification.getReportName()
        );
    }

}

