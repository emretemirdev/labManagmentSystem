package com.emretemir.laboratorymanagementsystem.repository;

import com.emretemir.laboratorymanagementsystem.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Report r WHERE r.id = :id")
    void deleteReportById(@Param("id") Long id);

    @Query("SELECT r FROM Report r WHERE " +
            "LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(r.surName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(r.user.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(r.user.username) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(r.diagnosisTitle) LIKE LOWER(CONCAT('%', :query, '%'))") // Add diagnosis title search
    List<Report> searchReports(@Param("query") String query);
}