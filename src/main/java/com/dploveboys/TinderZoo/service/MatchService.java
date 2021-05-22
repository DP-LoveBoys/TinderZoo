package com.dploveboys.TinderZoo.service;

import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.repositories.MatchRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MatchService { //o clasa mai struto - camila, lucreaza si pe tabelul de "matches" dar face si logica din spatele matching-ului propriu zis

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private InterestService interestService;

    public List<Long> getMatchesByUserId(Long userId) throws NotFoundException {
        List<Long> matchesIDs = matchRepository.getMatchIDsByUserId(userId);

        return matchesIDs;
    }
    public List<Long> getUserIDsByMatchId(Long matchId) throws NotFoundException {
        List<Long> userIDs = matchRepository.getUserIDsByMatchId(matchId);

        return userIDs;
    }

    public Map<Long, Integer> getMatches(Long ourId, List <Interest> interests) //this should return a list of matches IDs, based on our interests
    {
        HashMap<Long, Integer> matches_map = new HashMap<>();
        int totalScore = 0;
        int value = 1;
        for(Interest interest : interests) //go through each of our own interests
        {
            List <Long> users_with_common_interests;
            users_with_common_interests = interestService.getUsersExceptThisId(interest.getInterest_tag(), ourId); //get users with same interests as us
            for(Long userId : users_with_common_interests) //for each of these users, map their id and a score based on how frequent the overlapping interests are
            {
                if(matches_map.containsKey(userId)) //if the user id is already mapped, increment the stored value
                {
                    int temp_value = matches_map.get(userId);
                    matches_map.put(userId, temp_value++);
                }
                else
                {
                    matches_map.put(userId, value);
                }
            }
            totalScore++; //this is used to see how many interests we have and the total percentage of compatibility each user has with us (sort of)
        }
        matches_map = sortByValue(matches_map); //sort the map just because
        //the non-matches should be stored at the end of the queue in no particular order
        return matches_map;
    }

    public static HashMap<Long, Integer> sortByValue(HashMap<Long, Integer> matches_map) //used for help https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
    {
        HashMap<Long, Integer> temp_map = matches_map.entrySet()
                .stream()
                .sorted((elem1, elem2) -> elem1.getValue().compareTo(elem2.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (elem1, elem2) -> elem1, LinkedHashMap::new));
        return temp_map;
    }
}
