package com.emretemir.laboratorymanagementsystem.dto.Report;

public record ReportDTO(
        Long id,
        String name,
        String surName,
        Long identifyNumber,
        String diagnosisTitle,
        String diagnosisInfo,
        String reportDate,
        String reportPic,
        String laborantName,
        String laborantUsername,
        Long laborantId
) {}