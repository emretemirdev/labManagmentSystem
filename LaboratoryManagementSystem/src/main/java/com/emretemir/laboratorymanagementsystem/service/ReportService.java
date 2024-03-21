package com.emretemir.laboratorymanagementsystem.service;

import com.emretemir.laboratorymanagementsystem.model.Report;
import com.emretemir.laboratorymanagementsystem.repository.ReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ReportService {
    private ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }

    public List<Report> getAllReports(){
        return reportRepository.findAll();
    }


    public void saveReport(Report newReport) {
        reportRepository.save(newReport);
    }


    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Transactional
    public Report createReport(Report newReport) {
        return reportRepository.save(newReport);
    }

    @Transactional
    public Report updateReport(Long reportId, Report reportDetails) {
       Report report =  reportRepository.findById(reportId).orElseThrow(()-> new RuntimeException("Report not found for id: " + reportId));
        report.setName(reportDetails.getName());
        report.setSurName(reportDetails.getSurName());
        report.setIdentifyNumber(reportDetails.getIdentifyNumber());
        report.setDiagnosisTitle(reportDetails.getDiagnosisTitle());
        report.setDiagnosisInfo(reportDetails.getDiagnosisInfo());
        report.setReportDate(reportDetails.getReportDate());
        report.setReportPic(reportDetails.getReportPic());
        return reportRepository.save(report);
    }


}
