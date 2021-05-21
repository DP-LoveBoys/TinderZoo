package com.dploveboys.TinderZoo.service;

import com.dploveboys.TinderZoo.model.Preference;
import com.dploveboys.TinderZoo.repositories.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PreferenceService {

    @Autowired
    private PreferenceRepository preferenceRepository;

    public Preference getPreferences(Long userId){
        return preferenceRepository.findByUserId(userId);
    }
}
