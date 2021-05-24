package com.dploveboys.TinderZoo.repositories;

import com.dploveboys.TinderZoo.model.Preference;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PreferenceRepository extends CrudRepository<Preference,Long> {
    Preference findByUserId(Long userId);
}
