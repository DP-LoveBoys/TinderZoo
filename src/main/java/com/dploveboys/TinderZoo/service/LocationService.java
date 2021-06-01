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
        if(locationRepository.existsById(userId) == false) {

            Location temp_location = new Location();
            temp_location.setUserId(userId);
            temp_location.setLatitude(latitude);
            temp_location.setLongitude(longitude);
            locationRepository.save(temp_location);
        }
        else
        {
            locationRepository.updateLocation(userId, latitude, longitude);
        }
    }

    public Location getLocation(Long userId){

        Location temp_location;
        List<Double> coords =  locationRepository.getUserLocation(userId);
        if(coords == null)
        {
            temp_location = new Location(0.0, 0.0);
        }
        else
        {
            temp_location = new Location(coords.get(0), coords.get(1));
        }
        return temp_location;
    }

    public Double getDistance(Long ourId, Long userId)
    {
        Location ourLocation = this.getLocation(ourId);
        Location theirLocation = this.getLocation(userId);

        Double distance = HaversineAlgorithm.HaversineInKM(ourLocation.getLatitude(), ourLocation.getLongitude(), theirLocation.getLatitude(), theirLocation.getLongitude());

        return distance;
    }

    public Double getCoordinates(String address)
    {
        return 0.0;
    }

    public List <Long> getNearbyUsers(Long ourId, Double preferedDistance)
    {
        Iterable<Location> allUsers = locationRepository.findAll();
        /*
        for(Location location : allUsers)
        {

        }


         */
        return null;
    }

}
