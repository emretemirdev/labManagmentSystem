package com.emretemir.laboratorymanagementsystem.controller;

import com.emretemir.laboratorymanagementsystem.dto.ReportDTO;
import com.emretemir.laboratorymanagementsystem.model.Report;
import com.emretemir.laboratorymanagementsystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/report")
public class ReportController {

    ReportService reportService;


    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")

    @DeleteMapping( "delete/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable(name = "id") Long id){
        String message = reportService.deleteReport(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReportDTO>> getAllReportsDTO() {
        List<ReportDTO> dto = reportService.getAllReports();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable(name = "id") Long id){
        ReportDTO reportDTO = reportService.getReportById(id);
        return  ResponseEntity.ok(reportDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO) {
        ReportDTO createdReportDTO = reportService.createReport(reportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReportDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ReportDTO> updateReport(@PathVariable Long id, @RequestBody ReportDTO reportDTO) {
        ReportDTO updatedReportDTO = reportService.updateReport(id, reportDTO);
        return ResponseEntity.ok(updatedReportDTO);
    }

}
