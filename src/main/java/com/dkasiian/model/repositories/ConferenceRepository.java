package com.dkasiian.model.repositories;

import com.dkasiian.model.entities.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {

    List<Conference> findAllByOrderByDatetimeAsc();
    Conference findByTheme(String theme);

    @Modifying
    @Transactional
    @Query(value = "insert into users_conferences (user_id, conference_id) VALUES (:userId, :conferenceId)",
            nativeQuery = true)
    void subscribeUserToSelectedConference(@Param("userId")Long userId,
                                           @Param("conferenceId")Long conferenceId);

    @Modifying
    @Transactional
    @Query(value = "delete from users_conferences where user_id=:userId and conference_id=:conferenceId",
            nativeQuery = true)
    void unsubscribeUserToSelectedConference(@Param("userId")Long userId,
                                             @Param("conferenceId")Long conferenceId);

    @Modifying
    @Transactional
    @Query(value = "delete from reports_conferences where report_id=:userId and conference_id=:conferenceId",
            nativeQuery = true)
    void deleteReportFromSelectedConference(@Param("userId")Long reportId,
                                            @Param("conferenceId")Long conferenceId);

}
