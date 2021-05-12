package com.dploveboys.TinderZoo.repositories;

import com.dploveboys.TinderZoo.model.User;
import com.dploveboys.TinderZoo.model.UserCredential;
import org.springframework.data.repository.CrudRepository;

public interface UserCredentialRepository extends CrudRepository<UserCredential,Long> {
}
