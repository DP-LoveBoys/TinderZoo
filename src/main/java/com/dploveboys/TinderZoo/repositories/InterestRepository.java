package com.dploveboys.TinderZoo.repositories;

import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.model.Photo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InterestRepository extends CrudRepository<Interest,Long> {
    @Query(
            value = "SELECT id, user_id, interest_tag FROM tinderzoo_dev.interests WHERE user_id = ?1",
            nativeQuery = true)
    List<Interest> getInterestsByUserId(Long userId);

    @Query(
            value = "SELECT user_id FROM interests WHERE interest_tag = ?1",
            nativeQuery = true)
    List<Long> getUserIDsByInterests(String interest_tag);

    @Query(
            value = "DELETE FROM interests WHERE user_id = ?1 AND interest_tag = ?2",
            nativeQuery = true)
    void deleteByTag(Long userId, String interest_tag);

    @Query(
            value = "SELECT user_id FROM interests WHERE user_id != ?2 AND interest_tag = ?1",
            nativeQuery = true)
    List<Long> getUserIDsByInterestsButNotThisUser(String interest, Long thisId);
}
