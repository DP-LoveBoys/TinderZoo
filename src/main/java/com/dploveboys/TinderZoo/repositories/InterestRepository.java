package com.dploveboys.TinderZoo.repositories;

import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.model.Photo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InterestRepository extends CrudRepository<Interest,Long> {
    List<Interest> findByUserId(Long userId);

    @Query(
            value = "DELETE FROM interests WHERE id = ?1 AND interest_tag = ?2",
            nativeQuery = true)
    void deleteByTag(Long userId, String interest_tag);
}
