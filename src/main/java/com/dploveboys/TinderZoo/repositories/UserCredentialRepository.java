package com.dploveboys.TinderZoo.repositories;

import com.dploveboys.TinderZoo.model.UserCredential;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserCredentialRepository extends CrudRepository<UserCredential,Long> {
    @Query(
            value = "select * from credentials where email = ?1", //do a querry on the credentials table to find the user we need, by email
            nativeQuery = true)   // ?1 means first parameter passed to the method (as I understand it)
    UserCredential findByEmail(String email);
    @Query(
            value = "DELETE FROM credentials WHERE id = ?1",
            nativeQuery = true)
    void deleteProfile(Long userId);


    @Query(
            value = "select id from credentials where email = ?1",
            nativeQuery = true)
    Long findIdByEmail(String email);

    @Query(
            value = "select id from credentials where id != ?1",
            nativeQuery = true)
    List<Long> getAllUsersExcept(Long ourId);
}
