package com.dkasiian.controllers;

import com.dkasiian.model.entities.Report;
import com.dkasiian.model.services.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value="Conference Management System", tags = "report")
@RestController
@RequestMapping("/reports")
public class ReportRestController {

    private ReportService reportService;

    @Autowired
    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @ApiOperation("Get a list of reports")
    @GetMapping
    public ResponseEntity<List<Report>> getReports(){
        return new ResponseEntity<>(reportService.getAllReports(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority(T(com.dkasiian.model.entities.Role).SPEAKER," +
                                  "T(com.dkasiian.model.entities.Role).ADMIN)")
    @ApiOperation("Update a report")
    @PutMapping
    public ResponseEntity<Report> updateReport(@Valid @RequestBody Report report){
        return reportService.getReportById(report.getId())
                .map(oldReport -> new ResponseEntity<>(reportService.updateReport(oldReport, report), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @ApiOperation("Get a report by Id")
    @GetMapping("{reportId}")
    public ResponseEntity<Report> getReportById(@PathVariable("reportId") Long reportId){
        return reportService.getReportById(reportId)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PreAuthorize("hasAnyAuthority(T(com.dkasiian.model.entities.Role).SPEAKER," +
                                  "T(com.dkasiian.model.entities.Role).ADMIN)")
    @ApiOperation("Delete a report by Id")
    @DeleteMapping("{reportId}")
    public ResponseEntity deleteReportById(@PathVariable("reportId") Long reportId){
        return reportService.getReportById(reportId)
                .map(value -> {
                    reportService.deleteReportById(reportId);
                    return new ResponseEntity(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity(HttpStatus.BAD_REQUEST));
    }
}
