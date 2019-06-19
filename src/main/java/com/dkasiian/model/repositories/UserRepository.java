package com.dkasiian.model.repositories;

import com.dkasiian.model.entities.Role;
import com.dkasiian.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);
    Optional<User> findByIdAndRole(Long id, Role role);

    @Modifying
    @Transactional
    @Query(value = "insert into users_speakers_rating (user_id, speaker_id, speaker_rating, speaker_bonuses) " +
            "VALUES (:listenerId, :speakerId, :speakerRating, :speakerBonuses)", nativeQuery = true)
    void setRatingAndBonuses(@Param("listenerId")Long listenerId,
                             @Param("speakerId")Long speakerId,
                             @Param("speakerRating")Double speakerRating,
                             @Param("speakerBonuses")Integer speakerBonuses);

    @Modifying
    @Transactional
    @Query(value = "update users_speakers_rating set speaker_rating=:speakerRating, speaker_bonuses=:speakerBonuses " +
            "where user_id=:listenerId and speaker_id=:speakerId",
            nativeQuery = true)
    void updateRatingAndBonuses(@Param("listenerId")Long listenerId,
                                @Param("speakerId")Long speakerId,
                                @Param("speakerRating")Double speakerRating,
                                @Param("speakerBonuses")Integer speakerBonuses);

    @Transactional
    @Query(value = "select count(1) from users_speakers_rating where user_id=:userId and speaker_id=:speakerId",
            nativeQuery = true)
    Integer getRecordCount(@Param("userId")Long userId,
                           @Param("speakerId")Long speakerId);

}
