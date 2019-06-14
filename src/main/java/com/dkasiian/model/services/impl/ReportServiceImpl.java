package com.dkasiian.model.services.impl;

import com.dkasiian.model.entities.Report;
import com.dkasiian.model.repositories.ReportRepository;
import com.dkasiian.model.services.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    private ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public void deleteReportById(Long id) {
        reportRepository.deleteById(id);
    }

    @Override
    public Report saveReport(Report report) {
        log.info("Call saveConference FROM ReportServiceImpl");
        return reportRepository.save(report);
    }

    @Override
    public Report updateReport(Report oldReport, Report report) {
        report.setSpeaker(oldReport.getSpeaker());
        report.setConferences(oldReport.getConferences());
        return reportRepository.save(report);
    }
}
