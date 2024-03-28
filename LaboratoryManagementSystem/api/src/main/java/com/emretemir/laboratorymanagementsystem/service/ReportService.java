package com.emretemir.laboratorymanagementsystem.service;

import com.emretemir.laboratorymanagementsystem.dto.ReportDTO;
import com.emretemir.laboratorymanagementsystem.model.Report;
import com.emretemir.laboratorymanagementsystem.model.User;
import com.emretemir.laboratorymanagementsystem.repository.ReportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserService userService;
    private final NotificationService notificationService;

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);


    @Autowired
    public ReportService(UserService userService, ReportRepository reportRepository, NotificationService notificationService) {
        this.userService = userService;
        this.reportRepository = reportRepository;
        this.notificationService = notificationService;
    }

    // TODO
    //  DELETE YAPIYORUM FAKAT RAPOR SİLMİYOR !
    //   Rapor işlemini 200 döndürüyor ama dbden silinmiyor



    @Transactional
    public String deleteReport(Long id) {
        logger.info("Rapor silme işlemi başlatılıyor. Rapor ID: {}", id);

        try {
            Report report = reportRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Rapor bulunamadı, ID: " + id)
            );
            User user = userService.findById(report.getUser().getId()).orElseThrow(
                    () -> new EntityNotFoundException("Kullanıcı bulunamadı, ID: " + report.getUser().getId())
            );
            String laborantName = user.getName();

            reportRepository.deleteById(id);
            logger.info("Rapor ID: {} başarıyla silindi.", id);

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
            logger.error("Rapor silme işlemi sırasında bir hata oluştu: {}", e.getMessage());
            throw e;
        }
    }


    public ReportDTO getReportById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Report not found, ID: " + id));
        return convertToReportDTO(report);
    }

    @Transactional
    public ReportDTO createReport(ReportDTO reportDTO) {
        User user = userService.findById(reportDTO.laborantId()).orElseThrow(
                () -> new EntityNotFoundException("Kullanıcı bulunamadı, ID: " + reportDTO.laborantId())
        );

        Report report = new Report();
        report.setName(reportDTO.name());
        report.setSurName(reportDTO.surName());
        report.setIdentifyNumber(reportDTO.identifyNumber());
        report.setDiagnosisTitle(reportDTO.diagnosisTitle());
        report.setDiagnosisInfo(reportDTO.diagnosisInfo()); // Bu alanın eksik olduğu belirlendi
        report.setReportDate(reportDTO.reportDate());
        report.setReportPic(reportDTO.reportPic());
        report.setUser(user);
        report = reportRepository.save(report);

        // Bildirim oluştur
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
