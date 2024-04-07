package com.emretemir.laboratorymanagementsystem.controller;

import com.emretemir.laboratorymanagementsystem.core.constants.ApiPathConstants;
import com.emretemir.laboratorymanagementsystem.dto.Notification.NotificationDTO;
import com.emretemir.laboratorymanagementsystem.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllNotifications() {
        try {
            List<NotificationDTO> notifications = notificationService.getAllNotifications();
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            log.error("Error fetching notifications", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching notifications");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllNotifications() {
        try {
            notificationService.deleteAllNotifications();
            return ResponseEntity.ok("Tüm bildirimler başarıyla silindi");
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Silerken hata oluştu.");
        }
    }


}
