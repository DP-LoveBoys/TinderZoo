package com.dploveboys.TinderZoo.service;


import com.dploveboys.TinderZoo.model.User;
import com.dploveboys.TinderZoo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(Long id)
    {
        return userRepository.findById(id);
    }
}