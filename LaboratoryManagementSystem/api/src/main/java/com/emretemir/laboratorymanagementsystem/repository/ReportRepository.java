package com.emretemir.laboratorymanagementsystem.repository;

import com.emretemir.laboratorymanagementsystem.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface ReportRepository extends JpaRepository<Report, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Report r WHERE r.id = ?1")
    void deleteReportById(Long id);
}