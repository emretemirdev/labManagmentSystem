package com.emretemir.laboratorymanagementsystem.repository;

import com.emretemir.laboratorymanagementsystem.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
