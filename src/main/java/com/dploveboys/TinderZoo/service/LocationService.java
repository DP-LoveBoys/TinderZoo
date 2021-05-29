package com.dploveboys.TinderZoo.service;

import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.model.Location;
import com.dploveboys.TinderZoo.repositories.InterestRepository;
import com.dploveboys.TinderZoo.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("locationService")
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public void saveUserLocation(Long userId, Double latitude, Double longitude){
        Location temp_location = new Location();
        temp_location.setUserId(userId);
        temp_location.setLatitude(latitude);
        temp_location.setLongitude(longitude);
        locationRepository.save(temp_location);
    }

    public Location getLocation(Long userId){

        List<Double>  coords =  locationRepository.getUserLocation(userId);

        Location temp_location = new Location(coords.get(0), coords.get(1));
        return temp_location;
    }

}
