package com.dkasiian.model.services;

import com.dkasiian.model.entities.Conference;

import java.util.List;
import java.util.Optional;

public interface ConferenceService {
    Optional<Conference> getConferenceById(Long id);
    List<Conference> getAllConferences();
    Conference saveConference(Conference conference);
    Conference updateConference(Conference oldConference, Conference newConference);
    void deleteConferenceById(Long id);
    Conference findByTheme(String theme);

    void subscribeListenerToSpecifiedConference(Long userId, Long conferenceId);
    void unsubscribeListenerFromSpecifiedConference(Long userId, Long conferenceId);
    void deleteReportFromSpecifiedConference(Long reportId, Long conferenceId);

}
