package com.emretemir.laboratorymanagementsystem.controller;

import com.emretemir.laboratorymanagementsystem.model.Report;
import com.emretemir.laboratorymanagementsystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/report")
public class ReportController {

    ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping( "delete/{id}")
    public String deleteReport(@PathVariable(name = "id") Long id){
        reportService.deleteReport(id);
        return "redirect:/";
    }

    @GetMapping("all")
    public String getAllReports(){
        return reportService.getAllReports().toString();
    }

    @PostMapping("save")
    public String saveReport(){
        return "redirect:/";
    }

    @GetMapping("get/{id}")
    public String getReportById(@PathVariable(name = "id") Long id){
        return reportService.getReportById(id).toString();
    }

    @PostMapping("/create")
    public String createReport(@RequestBody Report report) {
        Report createdReport = reportService.createReport(report);
        return "Rapor başarıyla oluşturuldu: " + createdReport.getId();
    }

    @RequestMapping("update/{id}")
    public String updateReport(@PathVariable(name = "id") Long id){
        return "redirect:/";
    }

}
