package com.dploveboys.TinderZoo.model;

import com.dploveboys.TinderZoo.service.InterestService;
import com.dploveboys.TinderZoo.service.MatchService;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;

public class LoveRadar {

    private UserData user;

    private List<Interest> interests;
    private Map<Long, Integer> matches; //the matches returned will have a score for each userId (how much in common we have with the matches basically)

    private InterestService interestService;
    private MatchService matchService;

    public LoveRadar(UserData user){
        this.user = user;
    }

    public Map<Long, Integer> searchForLove()
    {
        Long our_id = user.getId();
        interests = interestService.getInterests(our_id);

        matches = matchService.getMatches(our_id, interests);

        return matches;
    }

    public void giveMatchResponse(Long matchId, MatchResponseProvider matchResponseProvider)
    {
        //go through matches here, give MATCH or NOT_INTERESTED
    }
}