package com.dploveboys.TinderZoo.repositories;

import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.model.Match;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepository extends CrudRepository<Match,Long> {
    @Query(
            value = "SELECT match_id FROM matches WHERE user_id = ?1",
            nativeQuery = true)
    List<Long> getMatchIDsByUserId(Long userId);

    @Query(
            value = "SELECT user_id FROM matches WHERE match_id = ?1",
            nativeQuery = true)
    List<Long> getUserIDsByMatchId(Long match_id);

    @Query(
            value = "DELETE FROM matches WHERE user_id = ?1 AND match_id = ?2",
            nativeQuery = true)
    void deleteMatch(Long userId, Long match_id);
}
