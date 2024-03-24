package com.emretemir.laboratorymanagementsystem.dto;

public record ReportDTO(
        Long id,
        String name,
        String surName,
        Long identifyNumber,
        String diagnosisTitle,
        String diagnosisInfo,
        String reportDate,
        byte[] reportPic,
        String laborantName,
        String laborantUsername,
        Long laborantId
) {}