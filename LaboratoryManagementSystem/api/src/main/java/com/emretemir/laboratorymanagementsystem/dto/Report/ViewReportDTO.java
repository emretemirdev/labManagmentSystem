package com.emretemir.laboratorymanagementsystem.dto.Report;

public record ViewReportDTO(
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