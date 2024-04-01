package com.emretemir.laboratorymanagementsystem.service;

import com.emretemir.laboratorymanagementsystem.dto.Report.ReportDTO;
import com.emretemir.laboratorymanagementsystem.model.Report;
import com.emretemir.laboratorymanagementsystem.model.User;
import com.emretemir.laboratorymanagementsystem.repository.ReportRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserService userService;
    private final NotificationService notificationService;
    private final AwsS3ReportPicService storageService;


    @Autowired
    public ReportService(UserService userService, ReportRepository reportRepository, NotificationService notificationService, AwsS3ReportPicService storageService) {
        this.userService = userService;
        this.reportRepository = reportRepository;
        this.notificationService = notificationService;
        this.storageService = storageService;
    }


    @Transactional
    public String deleteReport(Long id) {
        try {
            Report report = reportRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Rapor bulunamadı, ID: " + id)
            );
            User user = userService.findById(report.getUser().getId()).orElseThrow(
                    () -> new EntityNotFoundException("Kullanıcı bulunamadı, ID: " + report.getUser().getId())
            );
            String laborantName = user.getName();

            reportRepository.deleteById(id);
            notificationService.createNotification(
                    "SILME",
                    id,
                    "Rapor başarıyla silindi:",
                    user.getId(),
                    laborantName,
                    report.getName()
            );
            return "ID: " + id + " numaralı rapor başarıyla silindi.";
        } catch (Exception e) {
            throw e;
        }
    }


    public ReportDTO getReportById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Report not found, ID: " + id));
        return convertToReportDTO(report);
    }

    @Transactional
    public ReportDTO createReport(ReportDTO reportDTO, MultipartFile reportPic) {
        User user = userService.findById(reportDTO.laborantId()).orElseThrow(
                () -> new EntityNotFoundException("Kullanıcı bulunamadı, ID: " + reportDTO.laborantId())
        );

        String reportPicUrl = null;
        if (reportPic != null && !reportPic.isEmpty()) {
            reportPicUrl = storageService.uploadFile(reportPic);
        }


        Report report = new Report();
        report.setName(reportDTO.name());
        report.setSurName(reportDTO.surName());
        report.setIdentifyNumber(reportDTO.identifyNumber());
        report.setDiagnosisTitle(reportDTO.diagnosisTitle());
        report.setDiagnosisInfo(reportDTO.diagnosisInfo());
        report.setReportDate(reportDTO.reportDate());
        report.setReportPic(reportPicUrl);
        report.setUser(user);
        report = reportRepository.save(report);

        notificationService.createNotification(
                "OLUSTURMA",
                report.getId(),
                "Yeni rapor oluşturuldu: ",
                user.getId(),
                user.getName(),
                report.getName()
        );

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
        notificationService.createNotification(
                "GUNCELLEME",
                report.getId(),
                "Rapor başarıyla güncellendi: ",
                report.getUser().getId(),
                report.getUser().getName(),
                report.getName()
        );

        return convertToReportDTO(report);
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
