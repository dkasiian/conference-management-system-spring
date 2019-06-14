package com.dkasiian.model.services;

import com.dkasiian.model.entities.Report;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    Optional<Report> getReportById(Long id);
    List<Report> getAllReports();
    Report saveReport(Report report);
    Report updateReport(Report oldReport, Report report);
    void deleteReportById(Long id);
}
