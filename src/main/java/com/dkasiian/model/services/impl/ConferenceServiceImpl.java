package com.dkasiian.model.services.impl;

import com.dkasiian.model.entities.Conference;
import com.dkasiian.model.repositories.ConferenceRepository;
import com.dkasiian.model.services.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    private ConferenceRepository conferenceRepository;

    @Autowired
    public ConferenceServiceImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public List<Conference> getAllConferences() {
        return conferenceRepository.findAllByOrderByDatetimeAsc();
    }

    @Override
    public Optional<Conference> getConferenceById(Long id) {
        return conferenceRepository.findById(id);
    }

    @Override
    public Conference saveConference(Conference conference) {
        return conferenceRepository.save(conference);
    }

    @Override
    public Conference updateConference(Conference oldConference, Conference conference) {
        conference.setReports(oldConference.getReports());
        conference.setListeners(oldConference.getListeners());
        return conferenceRepository.save(conference);
    }

    @Override
    public void deleteConferenceById(Long id) {
        conferenceRepository.deleteById(id);
    }

    @Override
    public Conference findByTheme(String theme) {
        return conferenceRepository.findByTheme(theme);
    }

    @Override
    public void subscribeListenerToSpecifiedConference(Long userId, Long conferenceId) {
        conferenceRepository.subscribeUserToSelectedConference(userId, conferenceId);
    }

    @Override
    public void unsubscribeListenerFromSpecifiedConference(Long userId, Long conferenceId){
        conferenceRepository.unsubscribeUserToSelectedConference(userId, conferenceId);
    }

    @Override
    public void deleteReportFromSpecifiedConference(Long reportId, Long conferenceId) {
        conferenceRepository.deleteReportFromSelectedConference(reportId, conferenceId);
    }
}
