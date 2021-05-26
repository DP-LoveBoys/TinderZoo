package com.dploveboys.TinderZoo.repositories;

import com.dploveboys.TinderZoo.model.Match;
import com.dploveboys.TinderZoo.model.MatchResponseProvider;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

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

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE matches SET match_response_provider = ?3 WHERE user_id = ?1 AND match_id = ?2",
            nativeQuery = true)
    void updateOurResponse(Long our_id, Long their_id, String our_response);

    @Query(
            value = "SELECT * FROM matches WHERE user_id = ?1 AND match_id = ?2",
            nativeQuery = true)
    Match getPair(Long our_id, Long their_id);

    @Query(
            value = "SELECT user_id FROM matches WHERE match_id = ?1 AND match_response_provider = ?2",
            nativeQuery = true)
    List<Long> getUsersForMatchId(Long their_id, String their_response);

    @Query(
            value = "SELECT user_id FROM matches WHERE match_id = ?1 AND match_response_provider = ?2",
            nativeQuery = true)
    List<Long> getMatchesForUserId(Long our_id, String our_response);

    @Query(
            value = "SELECT match_id FROM matches WHERE user_id = ?1 AND user_response_provider = ?2 AND match_response_provider = ?2",
            nativeQuery = true)
    List<Long> getConfirmedMatchesId(Long our_id, String response);


}
