package com.dploveboys.TinderZoo.service;


import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.model.Match;
import com.dploveboys.TinderZoo.model.UserData;
import com.dploveboys.TinderZoo.repositories.InterestRepository;
import com.dploveboys.TinderZoo.repositories.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDataService {
    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    public Optional<UserData> getUserById(Long id)
    {
        return userDataRepository.findById(id);
    }

    public void addUserData(UserData userData){
        userDataRepository.save(userData);
    }

    public void updateUserData(Long userId,UserData newUserData){
        UserData oldUserData=getUserById(userId).get();
        newUserData.setDescription(oldUserData.getDescription());
        addUserData(newUserData);
    }

    public void updateDescription(Long userId,String description){
        UserData userData=getUserById(userId).get();
        userData.setDescription(description.substring(3,description.length()-6));
        userDataRepository.save(userData);
    }

}
