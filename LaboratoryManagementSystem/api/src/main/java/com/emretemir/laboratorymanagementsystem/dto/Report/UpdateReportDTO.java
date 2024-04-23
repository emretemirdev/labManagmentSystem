package com.emretemir.laboratorymanagementsystem.dto.Report;

public record UpdateReportDTO(
        Long id,
        String name,
        String surName,
        String diagnosisTitle,
        String diagnosisInfo
) {}