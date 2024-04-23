package com.emretemir.laboratorymanagementsystem.controller;

import com.emretemir.laboratorymanagementsystem.dto.Report.UpdateReportDTO;
import com.emretemir.laboratorymanagementsystem.dto.Report.ViewReportDTO;
import com.emretemir.laboratorymanagementsystem.dto.Report.CreateReportDTO;
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

    private final ReportService reportService;
    private final ObjectMapper objectMapper;


    public ReportController(ReportService reportService) {
        this.objectMapper = new ObjectMapper();
        this.reportService = reportService;

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping( ApiPathConstants.DELETE_REPORT)
    public ResponseEntity<String> deleteReport(@PathVariable(name = "id") Long id){
        String message = reportService.deleteReport(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping(ApiPathConstants.GET_ALL_REPORTS)
    public ResponseEntity<List<ViewReportDTO>> getAllReportsDTO() {
        List<ViewReportDTO> dto = reportService.getAllReports();
        return ResponseEntity.ok(dto);
    }

    @GetMapping(ApiPathConstants.GET_REPORT_BY_ID)
    public ResponseEntity<ViewReportDTO> getReportById(@PathVariable(name = "id") Long id){
        ViewReportDTO viewReportDTO = reportService.getReportById(id);
        return  ResponseEntity.ok(viewReportDTO);
    }

    @PostMapping(ApiPathConstants.CREATE_REPORT)
    public ResponseEntity<ViewReportDTO> createReport(
            @RequestParam("report") String reportAsJson,
            @RequestParam("reportPic") MultipartFile file) throws JsonProcessingException {
        CreateReportDTO createReportDTO = objectMapper.readValue(reportAsJson, CreateReportDTO.class);

        ViewReportDTO createdViewReportDTO = reportService.createReport(createReportDTO, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdViewReportDTO);
    }

    @PutMapping(ApiPathConstants.UPDATE_REPORT)
    public ResponseEntity<ViewReportDTO> updateReport(@PathVariable Long id, @RequestBody UpdateReportDTO updateReportDTO) {
        ViewReportDTO updatedViewReportDTO = reportService.updateReport(id, updateReportDTO);
        return ResponseEntity.ok(updatedViewReportDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ViewReportDTO>> searchReports(@RequestParam(required = false) String query) {
        List<ViewReportDTO> reports = reportService.searchReports(query);
        return ResponseEntity.ok(reports);
    }

}
