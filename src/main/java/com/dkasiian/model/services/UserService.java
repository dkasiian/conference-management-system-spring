package com.dkasiian.model.services;

import com.dkasiian.model.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findUserByLogin(String login);
    Optional<User> getUserById(Long id);
    Optional<User> getSpeakerById(Long speakerId);
    List<User> getAllUsers();
    User saveUser(User user);
    void deleteUserById(Long id);
    void setRatingAndBonuses(Long listenerId, Long speakerId, Double rating, Integer bonuses);
    void updateRatingAndBonuses(Long listenerId, Long speakerId, Double rating, Integer bonuses);
}
