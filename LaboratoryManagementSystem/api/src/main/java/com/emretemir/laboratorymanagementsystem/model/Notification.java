package com.emretemir.laboratorymanagementsystem.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "notification_type")
    private String notificationType;

    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "message")
    private String message;

    @Column(name = "laborant_id")
    private Long laborantId;

    @Column(name = "laborant_name")
    private String laborantName;

    @Column(name = "report_name")
    private String reportName;

    public Notification(Date createdAt, String notificationType, Long reportId, String message) {
        this.createdAt = createdAt;
        this.notificationType = notificationType;
        this.reportId = reportId;
        this.message = message;
    }

}
