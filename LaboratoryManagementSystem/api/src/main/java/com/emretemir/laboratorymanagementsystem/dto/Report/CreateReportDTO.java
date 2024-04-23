package com.emretemir.laboratorymanagementsystem.dto.Report;

public record CreateReportDTO(
        String name,
        String surName,
        Long identifyNumber,
        String diagnosisTitle,
        String diagnosisInfo,
        String reportDate,
        String reportPic,
        Long laborantId
) {}
