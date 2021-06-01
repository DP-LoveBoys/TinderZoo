package com.dploveboys.TinderZoo.service;

import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.model.Location;
import com.dploveboys.TinderZoo.model.Match;
import com.dploveboys.TinderZoo.model.MatchResponseProvider;
import com.dploveboys.TinderZoo.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchService { //o clasa mai struto - camila, lucreaza si pe tabelul de "matches" dar face si logica din spatele matching-ului propriu zis

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private InterestService interestService;

    @Autowired
    private UserCredentialService userCredentialService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private LocationService locationService;


    public ArrayList<Long> getMatches(Long ourId, Double preferedDistance, Location locationOfUser) throws IOException//, List <Interest> interests) //this should return a list of matches IDs, based on our interests
    {
        List <Long> usersCloseToUs = locationService.getNearbyUsers(locationOfUser, preferedDistance);

        List <Interest> interests = interestService.getInterests(ourId);

        HashMap<Long, Integer> matches_map = new HashMap<>();
        int value = 1;
        int bonus_for_distance = 0;
        for(Interest interest : interests) //go through each of our own interests
        {
            List <Long> users_with_common_interests;
            users_with_common_interests = interestService.getUsersExceptThisId(interest.getInterest_tag(), ourId); //get users with same interests as us
            for(Long userId : users_with_common_interests) //for each of these users, map their id and a score based on how frequent the overlapping interests are
            {

                if(usersCloseToUs.contains(userId))
                {
                    if(matches_map.containsKey(userId)) //if the user id is already mapped, increment the stored value
                    {
                        int temp_value = matches_map.get(userId);
                        temp_value++;
                        temp_value += bonus_for_distance;
                        matches_map.put(userId, temp_value);
                    }
                    else
                    {
                        matches_map.put(userId, (value + bonus_for_distance));
                    }
                }
            }
        }
        //System.out.println("Matches before sort:" + matches_map);

        matches_map = sortByValue(matches_map); //sort the map just because
        System.out.println("Matches after sort:" + matches_map);
        //the non-matches should be stored at the end of the queue in no particular order
        ArrayList<Long> matches_with_priority = new ArrayList<> (matches_map.keySet());

        /* Remnant of the previous match recommendation system (before we also added the users that didn't have any interests similar to ours

        List<Long> all_users = userCredentialService.getAllUsersExcept(ourId);
        System.out.println("Matches with priority is:" + matches_with_priority);
        System.out.println("All users is:" + all_users);
        for(Long userId : all_users)
        {
            if(!matches_with_priority.contains(userId))
                matches_with_priority.add(userId);
        }
        */
        System.out.println("Just before exit: " + matches_with_priority);
        return matches_with_priority;
    }

    public static HashMap<Long, Integer> sortByValue(HashMap<Long, Integer> matches_map) //used for help https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
    {
        HashMap<Long, Integer> temp_map = matches_map.entrySet()
                .stream()
                .sorted((elem1, elem2) -> elem2.getValue().compareTo(elem1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (elem1, elem2) -> elem1, LinkedHashMap::new));
        return temp_map;
    }


    public List<Long> getUsersByMatchId(Long their_id, String their_response) {
        List<Long> userIDs = matchRepository.getUsersForMatchId(their_id, their_response);

        return userIDs;
    }

    public List<Long> getMatchesByUserId(Long our_id, String our_response) {
        List<Long> matches_ids_for_us = matchRepository.getMatchesForUserId(our_id, our_response);

        return matches_ids_for_us;
    }

    public List<Long> getConfirmedMatchesIDs(Long our_id, String response)
    {
        return (List<Long>) matchRepository.getConfirmedMatchesID(our_id, response);
    }

    public List<Long> getConfirmedMatchesIds(Long our_id, String response)
    {
         List<Long> matches1 = matchRepository.getConfirmedMatchesIdFromMatchId(our_id, response);
         List<Long> matches2 = matchRepository.getConfirmedMatchesIdFromUserId(our_id,response);
         matches1.addAll(matches2);

         return matches1;
    }

    public void unmatch(Long userId,Long matchId){

        String role;
        Match match = matchRepository.getPair(userId,matchId);
        if(match==null){
            role="match";
            match=matchRepository.getPair(matchId,userId);
        }
        else{
            role="user";
        }

        if(match==null){
            match=new Match();
            match.setUser_id(userId);
            match.setMatch_id(matchId);
            match.setMatchResponseProvider(MatchResponseProvider.UNKNOWN);
        }

        if(role.equals("user")) {
            match.setUserResponseProvider(MatchResponseProvider.NOT_INTERESTED);
        }
        else{
            match.setMatchResponseProvider(MatchResponseProvider.NOT_INTERESTED);
        }
        matchRepository.save(match);
    }

    public void match(Long userId,Long matchId){
        String role;
        Match match=matchRepository.getPair(userId,matchId);

        if(match==null){
            role="match";
            match=matchRepository.getPair(matchId,userId);
        }
        else{
            role="user";
        }

        if(match==null){
            match=new Match();
            match.setUser_id(userId);
            match.setMatch_id(matchId);
            match.setMatchResponseProvider(MatchResponseProvider.UNKNOWN);
        }
        if(role.equals("user")) {
            match.setUserResponseProvider(MatchResponseProvider.MATCH);
            if(match.getMatchResponseProvider()==MatchResponseProvider.MATCH){
                notificationService.addNotification(notificationService.createNotification(userId,matchId,"match"));
                notificationService.addNotification(notificationService.createNotification(matchId,userId,"match"));
            }
            else if(match.getMatchResponseProvider()==MatchResponseProvider.UNKNOWN){
                notificationService.addNotification(notificationService.createNotification(matchId,userId,"interest"));
            }
        }
        else{
            match.setMatchResponseProvider(MatchResponseProvider.MATCH);
            if(match.getUserResponseProvider()==MatchResponseProvider.MATCH){
                notificationService.addNotification(notificationService.createNotification(userId,matchId,"match"));
                notificationService.addNotification(notificationService.createNotification(matchId,userId,"match"));
            }
            else if(match.getUserResponseProvider()==MatchResponseProvider.UNKNOWN){
                notificationService.addNotification(notificationService.createNotification(matchId,userId,"interest"));
            }
        }
        matchRepository.save(match);

    }

    public Match getMatch(Long userId,Long matchId){
        Match match = matchRepository.getPair(userId,matchId);
        if(match==null){
            match=matchRepository.getPair(matchId,userId);
        }
        return match;
    }

    public boolean alreadyMatchedOrNotInterested(Long userId, Long anotherId)
    {
        List <String> responses = matchRepository.getResponses(userId, anotherId);
        if((responses.get(0).equals(responses.equals(1)) && responses.get(0).equals("MATCH")) ||
                (responses.get(0).equals(responses.equals(1)) && responses.get(0).equals("NOT_INTERESTED")))
            return true;
        return false;
    }
}
