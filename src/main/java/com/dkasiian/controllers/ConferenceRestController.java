package com.dkasiian.controllers;

import com.dkasiian.model.entities.Conference;
import com.dkasiian.model.entities.Report;
import com.dkasiian.model.entities.User;
import com.dkasiian.model.services.ConferenceService;
import com.dkasiian.model.services.ReportService;
import com.dkasiian.model.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Api(value="Conference Management System", tags = "conference")
@RestController
@RequestMapping("/conferences")
public class ConferenceRestController {

    private ConferenceService conferenceService;
    private ReportService reportService;
    private UserService userService;

    @Autowired
    public ConferenceRestController(ConferenceService conferenceService,
                                    ReportService reportService,
                                    UserService userService) {
        this.conferenceService = conferenceService;
        this.reportService = reportService;
        this.userService = userService;
    }

    @ApiOperation("Get a list of conferences")
    @GetMapping
    public ResponseEntity<List<Conference>> getConferences(){
        return new ResponseEntity<>(conferenceService.getAllConferences(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority(T(com.dkasiian.model.entities.Role).ADMIN)")
    @ApiOperation("Add a conference")
    @PostMapping
    public ResponseEntity<Conference> saveConference(@Valid @RequestBody Conference conference){
        return new ResponseEntity<>(conferenceService.saveConference(conference), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority(T(com.dkasiian.model.entities.Role).ADMIN)")
    @ApiOperation("Update a conference")
    @PutMapping
    public ResponseEntity<Conference> updateConference(@Valid @RequestBody Conference conference){
        return conferenceService.getConferenceById(conference.getId())
                .map(oldConference -> new ResponseEntity<>(
                        conferenceService.updateConference(oldConference, conference), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));

//        return new ResponseEntity<>(conferenceService.updateConference(conference), HttpStatus.OK);
    }

    @ApiOperation("Get a conference by Id")
    @GetMapping("{conferenceId}")
    public ResponseEntity<Conference> getConferenceById(@PathVariable Long conferenceId){
        return conferenceService.getConferenceById(conferenceId)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        //        return conferenceService.getConferenceById(conferenceId)
//                .orElseThrow(() -> new NotFoundException("Conference not found for this id :: " + conferenceId);
    }

    @PreAuthorize("hasAuthority(T(com.dkasiian.model.entities.Role).ADMIN)")
    @ApiOperation("Delete a conference by Id")
    @DeleteMapping("{conferenceId}")
    public ResponseEntity deleteConference(@PathVariable("conferenceId") Long conferenceId){
        return conferenceService.getConferenceById(conferenceId)
                .map(value -> {
                    conferenceService.deleteConferenceById(conferenceId);
                    return new ResponseEntity(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity(HttpStatus.BAD_REQUEST));


//        conferenceService.getConferenceById(conferenceId)
//                .orElseThrow(() -> new NotFoundException("Conference not found for this id :: " + conferenceId));
//        conferenceService.deleteConferenceById(conferenceId);
    }


    // Reports via related Conference
    @ApiOperation("Get all reports from the specified conference")
    @GetMapping("{conferenceId}/reports")
    public ResponseEntity<Set<Report>> getAllReportsFromSpecifiedConference(
            @PathVariable("conferenceId") Long conferenceId){
        return conferenceService.getConferenceById(conferenceId)
                .map(value -> new ResponseEntity<>(value.getReports(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));

//        Conference conference = conferenceService.getConferenceById(conferenceId)
//                .orElseThrow(() -> new NotFoundException("Conference not found for this id :: " + conferenceId));
//        return conference.getReports();
    }

    @PreAuthorize("hasAnyAuthority(T(com.dkasiian.model.entities.Role).SPEAKER," +
                                  "T(com.dkasiian.model.entities.Role).ADMIN)")
    @ApiOperation("Add a new report to the specified conference")
    @PostMapping("{conferenceId}/reports")
    public ResponseEntity<Report> addReportToConference(
            @PathVariable("conferenceId") Long conferenceId,
            @Valid @RequestBody Report report
    ){
        return conferenceService.getConferenceById(conferenceId)
                .map(value -> {
                    if (CollectionUtils.isEmpty(value.getReports()))
                        value.setReports(new HashSet<Report>(){ { add(report); } });
                    else
                        value.getReports().add(report);
                    return new ResponseEntity<>(reportService.saveReport(report), HttpStatus.CREATED);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));


//        Conference conference = conferenceService.getConferenceById(conferenceId)
//                .orElseThrow(() -> new NotFoundException("Conference not found for this id :: " + conferenceId));
//
//        if (conference.getReports() != null)
//            conference.getReports().add(report);
//        else
//            conference.setReports(new HashSet<Report>(){ { add(report); } });
//
//        return reportService.saveReport(report);
    }

    @ApiOperation("Get the specified report from the specified conference")
    @GetMapping("{conferenceId}/reports/{reportId}")
    public ResponseEntity<Report> getReportFromSpecifiedConference(
            @PathVariable("conferenceId") Long conferenceId,
            @PathVariable("reportId") Long reportId
    ){
        AtomicReference<Optional<ResponseEntity<Report>>> response = new AtomicReference<>(Optional.empty());
        conferenceService.getConferenceById(conferenceId).ifPresent(
                conference -> reportService.getReportById(reportId).ifPresent(
                        report -> {
                            if (conference.getReports().contains(report))
                                response.set(Optional.of(new ResponseEntity<>(report, HttpStatus.OK)));
                        }
                )
        );
        return response.get().orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));

//        Conference conference = conferenceService.getConferenceById(conferenceId)
//                .orElseThrow(() -> new NotFoundException("Conference not found for this id :: " + conferenceId));
//        Report report = reportService.getReportById(reportId)
//                .orElseThrow(() -> new NotFoundException("Report not found for this id :: " + reportId));
//        if (!conference.getReports().contains(report))
//            throw new NotFoundException("The specified report with id :: " + reportId +
//                    " is not on the conference with id :: " + conferenceId);
//        return report;
    }

    @PreAuthorize("hasAnyAuthority(T(com.dkasiian.model.entities.Role).SPEAKER," +
                                  "T(com.dkasiian.model.entities.Role).ADMIN)")
    @ApiOperation("Delete the specified report from the specified conference")
    @DeleteMapping("{conferenceId}/reports/{reportId}")
    public ResponseEntity deleteReportFromSpecifiedConference(
            @PathVariable("conferenceId") Long conferenceId,
            @PathVariable("reportId") Long reportId
    ){
        return conferenceService.getConferenceById(conferenceId)
                .map(conference -> reportService.getReportById(reportId)
                        .map(report -> {
                            if (conference.getReports().contains(report)) {
                                conferenceService.deleteReportFromSpecifiedConference(reportId, conferenceId);
                                return new ResponseEntity(HttpStatus.NO_CONTENT);
                            }
                            return new ResponseEntity(HttpStatus.BAD_REQUEST);
                        })
                        .orElseGet(() -> new ResponseEntity(HttpStatus.BAD_REQUEST))
                )
                .orElseGet(() -> new ResponseEntity(HttpStatus.BAD_REQUEST));

//        Conference conference = conferenceService.getConferenceById(conferenceId)
//                .orElseThrow(() -> new NotFoundException("Conference not found for this id :: " + conferenceId));
//        Report report = reportService.getReportById(reportId)
//                .orElseThrow(() -> new NotFoundException("Report not found for this id :: " + reportId));
//
//        if (!conference.getReports().contains(report))
//            throw new NotFoundException("The specified report with id :: " + reportId +
//                    " is not on the conference with id :: " + conferenceId);
//
//        conferenceService.deleteReportFromSpecifiedConference(reportId, conferenceId);
    }


    // Users via related Conference
    @PreAuthorize("hasAuthority(T(com.dkasiian.model.entities.Role).ADMIN)")
    @ApiOperation("Get all subscribers (listeners) from the specified conference")
    @GetMapping("{conferenceId}/users")
    public ResponseEntity<Set<User>> getAllListenersFromSpecifiedConference(
            @PathVariable("conferenceId") Long conferenceId
    ){
        return conferenceService.getConferenceById(conferenceId)
                .map(value -> new ResponseEntity<>(value.getListeners(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));

//        Conference conference = conferenceService.getConferenceById(conferenceId)
//                .orElseThrow(() -> new NotFoundException("Conference not found for this id :: " + conferenceId));
//        return conference.getListeners();
    }

    @PreAuthorize("hasAuthority(T(com.dkasiian.model.entities.Role).ADMIN)")
    @ApiOperation("Get the specified subscriber (listener) from the specified conference")
    @GetMapping("{conferenceId}/users/{userId}")
    public ResponseEntity<User> getListenerFromSpecifiedConference(
            @PathVariable("conferenceId") Long conferenceId,
            @PathVariable("userId") Long userId
    ){
        return conferenceService.getConferenceById(conferenceId)
                .map(conference -> userService.getUserById(userId)
                        .map(user -> {
                            if (conference.getListeners().contains(user))
                                return new ResponseEntity<>(user, HttpStatus.OK);
                            return new ResponseEntity<>(new User(), HttpStatus.BAD_REQUEST);
                        })
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST))
                )
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));


//        AtomicReference<Optional<ResponseEntity<User>>> response = new AtomicReference<>(Optional.empty());
//        conferenceService.getConferenceById(conferenceId).ifPresent(
//                conference -> userService.getUserById(userId).ifPresent(
//                        user -> {
//                            if (conference.getListeners().contains(user))
//                                response.set(Optional.of(new ResponseEntity<>(user, HttpStatus.OK)));
//                        }
//                )
//        );
//        return response.get().orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));

//        Conference conference = conferenceService.getConferenceById(conferenceId)
//                .orElseThrow(() -> new NotFoundException("Conference not found for this id :: " + conferenceId));
//        User listener = userService.getUserById(userId)
//                .orElseThrow(() -> new NotFoundException("User not found for this id :: " + userId));
//        if (!conference.getListeners().contains(listener))
//            throw new NotFoundException("User with id :: " + userId +
//                    " isn't subscribed on conference with id :: " + conferenceId);
//        return listener;
    }

    @ApiOperation("Subscribe (register) the specified user (listener) to the specified conference")
    @PostMapping("{conferenceId}/users/{userId}")
    public ResponseEntity subscribeListenerToSpecifiedConference(
            @PathVariable("conferenceId") Long conferenceId,
            @PathVariable("userId") Long userId
    ){
        return conferenceService.getConferenceById(conferenceId)
                .map(conference -> userService.getUserById(userId)
                        .map(user -> {
                            if (conference.getListeners().contains(user))
                                return new ResponseEntity(HttpStatus.CONFLICT);
                            conferenceService.subscribeListenerToSpecifiedConference(userId, conferenceId);
                            return new ResponseEntity(HttpStatus.OK);
                        })
                        .orElseGet(() -> new ResponseEntity(HttpStatus.BAD_REQUEST))
                )
                .orElseGet(() -> new ResponseEntity(HttpStatus.BAD_REQUEST));

//        Conference conference = conferenceService.getConferenceById(conferenceId)
//                .orElseThrow(() -> new NotFoundException("Conference not found for this id :: " + conferenceId));
//        User listener = userService.getUserById(userId)
//                .orElseThrow(() -> new NotFoundException("User not found for this id :: " + userId));
//
//        if (conference.getListeners().contains(listener))
//            throw new DuplicateSubmitException("User with id :: " + userId +
//                    " already subscribed on conference with id :: " + conferenceId);
//
//        conferenceService.subscribeListenerToSpecifiedConference(userId, conferenceId);
    }

    @ApiOperation("Unsubscribe (unregister) the specified user (listener) from the specified conference")
    @DeleteMapping("{conferenceId}/users/{userId}")
    public ResponseEntity unsubscribeListenerFromSpecifiedConference(
            @PathVariable("conferenceId") Long conferenceId,
            @PathVariable("userId") Long userId
    ){
        return conferenceService.getConferenceById(conferenceId)
                .map(conference -> userService.getUserById(userId)
                        .map(user -> {
                            if (!conference.getListeners().contains(user))
                                return new ResponseEntity(HttpStatus.CONFLICT);
                            conferenceService.unsubscribeListenerFromSpecifiedConference(userId, conferenceId);
                            return new ResponseEntity(HttpStatus.OK);
                        })
                        .orElseGet(() -> new ResponseEntity(HttpStatus.BAD_REQUEST))
                )
                .orElseGet(() -> new ResponseEntity(HttpStatus.BAD_REQUEST));

//        Conference conference = conferenceService.getConferenceById(conferenceId)
//                .orElseThrow(() -> new NotFoundException("Conference not found for this id :: " + conferenceId));
//        User listener = userService.getUserById(userId)
//                .orElseThrow(() -> new NotFoundException("User not found for this id :: " + conferenceId));
//
//        if (!conference.getListeners().contains(listener))
//            throw new NotFoundException("User with id :: " + userId +
//                    " isn't subscribed on conference with id :: " + conferenceId);
//
//        conferenceService.unsubscribeListenerFromSpecifiedConference(userId, conferenceId);
    }

    @PreAuthorize("hasAuthority(T(com.dkasiian.model.entities.Role).ADMIN)")
    @ApiOperation("Get statistic of specified conference")
    @GetMapping("{conferenceId}/stats")
    public ResponseEntity getConferencesStatistics(@PathVariable("conferenceId") Long conferenceId){
        return conferenceService.getConferenceById(conferenceId)
                .map(conference -> ResponseEntity.ok().body(new HashMap(){{
                    put("is_passed", conference.getDatetime().isBefore(LocalDateTime.now()));
                    put("reports_count", conference.getReports().size());
                    put("listeners_count", conference.getListeners().size());
                }}))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
