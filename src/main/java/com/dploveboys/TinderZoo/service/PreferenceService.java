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

    public void addPreference(Long userId,String preference){
        Preference userPreference=getPreferences(userId);

        if(userPreference==null){
            userPreference=new Preference();
            userPreference.setCloseAge(false);
            userPreference.setSameBreed(false);
            userPreference.setNearby(false);
            userPreference.setUserId(userId);
        }



        if(preference.equals("closeAge")){
            userPreference.setCloseAge(true);
        }
        if(preference.equals("sameBreed")){
            userPreference.setSameBreed(true);
        }
        if(preference.equals("nearby")){
            userPreference.setNearby(true);
        }

        preferenceRepository.save(userPreference);
    }

    public void deletePreference(Long userId,String preference){
        Preference userPreference=getPreferences(userId);

        if(preference.equals("closeAge")){
            userPreference.setCloseAge(false);
        }
        if(preference.equals("sameBreed")){
            userPreference.setSameBreed(false);
        }
        if(preference.equals("nearby")){
            userPreference.setNearby(false);
        }

        preferenceRepository.save(userPreference);
    }
}
