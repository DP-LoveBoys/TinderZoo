package com.dploveboys.TinderZoo.service;


import com.dploveboys.TinderZoo.model.UserData;
import com.dploveboys.TinderZoo.repositories.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDataService {

    @Autowired
    private UserDataRepository userDataRepository;

    public Optional<UserData> getUserById(Long id)
    {
        return userDataRepository.findById(id);
    }

    public void addUserData(UserData userData){
        userDataRepository.save(userData);
    }
}
