package com.emretemir.laboratorymanagementsystem.service;

import com.emretemir.laboratorymanagementsystem.dto.ReportDTO;
import com.emretemir.laboratorymanagementsystem.model.Report;
import com.emretemir.laboratorymanagementsystem.model.User;
import com.emretemir.laboratorymanagementsystem.repository.ReportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserService userService;

    public ReportService(UserService userService, ReportRepository reportRepository) {
        this.userService = userService;
        this.reportRepository = reportRepository;
    }

    public String deleteReport(Long id) {
        Report report = reportRepository.findById(id).orElse(null);
        if (report != null) {
            reportRepository.deleteById(id);
            return "Report with ID " + id + " was successfully deleted.";
        }
        return "Report with ID " + id + " not found.";
    }

    public ReportDTO getReportById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Report not found, ID: " + id));
        return convertToReportDTO(report);
    }

    @Transactional
    public ReportDTO createReport(ReportDTO reportDTO) {
        Report report = convertToReport(reportDTO);
        report = reportRepository.save(report);
        return convertToReportDTO(report);
    }

    public List<ReportDTO> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream().map(this::convertToReportDTO).collect(Collectors.toList());
    }

    @Transactional
    public ReportDTO updateReport(Long reportId, ReportDTO reportDTO) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Report not found with id: " + reportId));
        updateReportFromDTO(reportDTO, report);
        report = reportRepository.save(report);
        return convertToReportDTO(report);
    }

    private Report convertToReport(ReportDTO reportDTO) {
        User user = userService.findById(reportDTO.laborantId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Report report = new Report();
        report.setId(reportDTO.id());
        report.setName(reportDTO.name());
        report.setSurName(reportDTO.surName());
        report.setIdentifyNumber(reportDTO.identifyNumber());
        report.setDiagnosisTitle(reportDTO.diagnosisTitle());
        report.setDiagnosisInfo(reportDTO.diagnosisInfo());
        report.setReportDate(reportDTO.reportDate());
        report.setReportPic(reportDTO.reportPic());
        report.setUser(user);
        return report;
    }

    private void updateReportFromDTO(ReportDTO reportDTO, Report report) {
        report.setName(reportDTO.name());
        report.setSurName(reportDTO.surName());
        report.setIdentifyNumber(reportDTO.identifyNumber());
        report.setDiagnosisTitle(reportDTO.diagnosisTitle());
        report.setDiagnosisInfo(reportDTO.diagnosisInfo());
        report.setReportDate(reportDTO.reportDate());
        report.setReportPic(reportDTO.reportPic());
    }

    private ReportDTO convertToReportDTO(Report report) {
        return new ReportDTO(
                report.getId(),
                report.getName(),
                report.getSurName(),
                report.getIdentifyNumber(),
                report.getDiagnosisTitle(),
                report.getDiagnosisInfo(),
                report.getReportDate(),
                report.getReportPic(),
                report.getUser().getName(),
                report.getUser().getUsername(),
                report.getUser().getId()
        );
    }
}
