package com.dploveboys.TinderZoo.repositories;

import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.model.Location;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location,Long> {
    @Query(
            value = "SELECT latitude, longitude FROM user_location WHERE user_id = ?1",
            nativeQuery = true)
    List<Double> getUserLocation(Long userId);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE user_location SET latitude = ?2, longitude = ?3 WHERE user_id = ?1",
            nativeQuery = true)
    void updateLocation(Long userId, Double latitude, Double longitude);

}
