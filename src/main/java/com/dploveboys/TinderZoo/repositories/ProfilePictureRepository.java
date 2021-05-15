package com.dploveboys.TinderZoo.repositories;

import com.dploveboys.TinderZoo.model.ProfilePicture;
import org.springframework.data.repository.CrudRepository;

public interface ProfilePictureRepository extends CrudRepository<ProfilePicture,Long> {
    ProfilePicture findByUserId(Long userId);
}
