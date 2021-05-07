package com.dploveboys.TinderZoo.repositories;

import com.dploveboys.TinderZoo.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
