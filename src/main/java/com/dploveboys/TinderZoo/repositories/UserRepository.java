package com.dploveboys.TinderZoo.repositories;

import com.dploveboys.TinderZoo.model.UserData;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserData,Long> {
}
