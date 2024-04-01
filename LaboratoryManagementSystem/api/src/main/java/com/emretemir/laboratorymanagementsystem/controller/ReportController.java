package com.emretemir.laboratorymanagementsystem.controller;

import com.emretemir.laboratorymanagementsystem.dto.Report.ReportDTO;
import com.emretemir.laboratorymanagementsystem.core.constants.ApiPathConstants;
import com.emretemir.laboratorymanagementsystem.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(ApiPathConstants.REPORT_BASE_URL)
public class ReportController {

    ReportService reportService;


    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping( ApiPathConstants.DELETE_REPORT)
    public ResponseEntity<String> deleteReport(@PathVariable(name = "id") Long id){
        String message = reportService.deleteReport(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping(ApiPathConstants.GET_ALL_REPORTS)
    public ResponseEntity<List<ReportDTO>> getAllReportsDTO() {
        List<ReportDTO> dto = reportService.getAllReports();
        return ResponseEntity.ok(dto);
    }

    @GetMapping(ApiPathConstants.GET_REPORT_BY_ID)
    public ResponseEntity<ReportDTO> getReportById(@PathVariable(name = "id") Long id){
        ReportDTO reportDTO = reportService.getReportById(id);
        return  ResponseEntity.ok(reportDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<ReportDTO> createReport(
            @RequestParam("report") String reportAsJson,
            @RequestParam("reportPic") MultipartFile file) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            ReportDTO reportDTO = objectMapper.readValue(reportAsJson, ReportDTO.class);

            ReportDTO createdReportDTO = reportService.createReport(reportDTO, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReportDTO);
    }

    @PutMapping(ApiPathConstants.UPDATE_REPORT)
    public ResponseEntity<ReportDTO> updateReport(@PathVariable Long id, @RequestBody ReportDTO reportDTO) {
        ReportDTO updatedReportDTO = reportService.updateReport(id, reportDTO);
        return ResponseEntity.ok(updatedReportDTO);
    }

}
