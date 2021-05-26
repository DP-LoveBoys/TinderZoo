package com.dploveboys.TinderZoo.repositories;

import com.dploveboys.TinderZoo.model.UserData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserDataRepository extends CrudRepository<UserData,Long> {
    @Query(
            value = "SELECT * FROM user_data WHERE id = ?1",
            nativeQuery = true)
    UserData findUserByIdAsUserDataType(Long id);


    @Query(
            value = "SELECT * FROM user_data WHERE id = ?1",
            nativeQuery = true)
    UserData getActualUserDataById(Long id);
}
