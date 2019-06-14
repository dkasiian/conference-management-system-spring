package com.dkasiian.model.services.impl;

import com.dkasiian.controllers.exceptions.DuplicateSubmitException;
import com.dkasiian.model.entities.Role;
import com.dkasiian.model.entities.User;
import com.dkasiian.model.repositories.UserRepository;
import com.dkasiian.model.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceIml implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceIml(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void setRatingAndBonuses(Long listenerId, Long speakerId, Double rating, Integer bonuses) {
        if (userRepository.getRecordCount(listenerId, speakerId) > 0)
            throw new DuplicateSubmitException("Listener already set a rating this speaker!");
        userRepository.setRatingAndBonuses(listenerId, speakerId, rating, bonuses);
    }

    @Override
    public void updateRatingAndBonuses(Long listenerId, Long speakerId, Double rating, Integer bonuses) {
        userRepository.updateRatingAndBonuses(listenerId, speakerId, rating, bonuses);
    }

    @Override
    public Optional<User> getSpeakerById(Long speakerId) {
        return userRepository.findByIdAndRole(speakerId, Role.SPEAKER);
    }
}
