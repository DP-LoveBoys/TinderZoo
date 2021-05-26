package com.dploveboys.TinderZoo.repositories;

import com.dploveboys.TinderZoo.model.Role;
import com.dploveboys.TinderZoo.model.UserCredential;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {
    @Query(
            value = "select * from roles where name = ?1", //do a querry on the credentials table to find the user we need, by email
            nativeQuery = true)   // ?1 means first parameter passed to the method (as I understand it)
    Role findByName(String name);

}
