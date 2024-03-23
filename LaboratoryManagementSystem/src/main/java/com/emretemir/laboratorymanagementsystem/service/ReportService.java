package com.emretemir.laboratorymanagementsystem.service;

import com.emretemir.laboratorymanagementsystem.dto.ReportDTO;
import com.emretemir.laboratorymanagementsystem.model.Report;
import com.emretemir.laboratorymanagementsystem.model.User;
import com.emretemir.laboratorymanagementsystem.repository.ReportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportService {
    private ReportRepository reportRepository;
    private ModelMapper modelMapper;
    private UserService userService;


    public ReportService(UserService userService,
                         ReportRepository reportRepository,
                         ModelMapper modelMapper) {
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    public String deleteReport(Long id) {
        Report report = reportRepository.findById(id).orElse(null);
        if (report != null) {
            String reportName = report.getName();
            Long reportId = report.getId();
            reportRepository.deleteById(id);
            return "Id numarası " + reportId + " ve Adı " + reportName + " olan rapor başarıyla silindi";
        }
        return "İd numarası " + id + " olan rapor bulunamadı" ;
    }


    public ReportDTO getReportById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rapor bulunamadı, ID: " + id));
        return convertToReportDTO(report);
    }

    @Transactional
    public ReportDTO createReport(ReportDTO reportDTO) {
        // Kullanıcıyı ID'ye göre bul
        User user = userService.findById(reportDTO.laborantId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Rapor oluşturma işlemini gerçekleştir
        Report report = modelMapper.map(reportDTO, Report.class);
        report.setUser(user);
        report = reportRepository.save(report);

        // Oluşturulan raporu DTO'ya dönüştürerek döndür
        return modelMapper.map(report, ReportDTO.class);
    }

    public List<ReportDTO> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(this::convertToReportDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReportDTO saveReport(ReportDTO reportDTO) {
        // ReportDTO'dan Report'a dönüşüm
        Report report = modelMapper.map(reportDTO, Report.class);
        // Kaydet ve dönüştürülmüş Report nesnesini geri dön
        report = reportRepository.save(report);
        return modelMapper.map(report, ReportDTO.class);
    }

    @Transactional
    public ReportDTO updateReport(Long reportId, ReportDTO reportDTO) {
        // Mevcut raporu ID'ye göre bul
        Report existingReport = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Report not found with id: " + reportId));

        // Mevcut raporun kullanıcısını sakla
        User currentUser = existingReport.getUser();

        // DTO'dan model nesnesine dönüştür, ancak kullanıcıyı koru
        modelMapper.map(reportDTO, existingReport);
        existingReport.setUser(currentUser); // Raporun kullanıcısını geri yükle

        // Raporu güncelle
        Report updatedReport = reportRepository.save(existingReport);

        // Güncellenmiş raporu DTO'ya dönüştür ve döndür
        return modelMapper.map(updatedReport, ReportDTO.class);
    }

    public ReportDTO convertToReportDTO(Report report) {
        return new ReportDTO(
                report.getId(),
                report.getName(),
                report.getSurName(),
                report.getIdentifyNumber(),
                report.getDiagnosisTitle(),
                report.getDiagnosisInfo(),
                report.getReportDate(),
                report.getReportPic() != null ? report.getReportPic() : null,
                report.getUser().getName(),
                report.getUser().getUsername(),
                report.getUser().getId()
        );
    }
}
