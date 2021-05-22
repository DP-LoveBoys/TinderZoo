package com.dploveboys.TinderZoo.service;

import com.dploveboys.TinderZoo.model.AuthenticationProvider;
import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.repositories.MatchRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    public List<Long> getMatchesByUserId(Long userId) throws NotFoundException {
        List<Long> matchesIDs = matchRepository.getMatchIDsByUserId(userId);

        return matchesIDs;
    }
    public List<Long> getUserIDsByMatchId(Long matchId) throws NotFoundException {
        List<Long> userIDs = matchRepository.getUserIDsByMatchId(matchId);

        return userIDs;
    }
}
