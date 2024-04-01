package com.emretemir.laboratorymanagementsystem.controller;

import com.emretemir.laboratorymanagementsystem.core.constants.ApiPathConstants;
import com.emretemir.laboratorymanagementsystem.dto.Notification.NotificationDTO;
import com.emretemir.laboratorymanagementsystem.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPathConstants.NOTIFICATION_BASE_URL)
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationDTO createNotification(@RequestBody NotificationDTO notificationDTO) {
        return notificationService.createNotification(
                notificationDTO.notificationType(),
                notificationDTO.reportId(),
                notificationDTO.message(),
                notificationDTO.laborantId(),
                notificationDTO.laborantName(),
                notificationDTO.reportName()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<NotificationDTO> getAllNotifications() {
        return notificationService.getAllNotifications();
    }
}
