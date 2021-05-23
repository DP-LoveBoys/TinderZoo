package com.dploveboys.TinderZoo.service;


import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.model.Match;
import com.dploveboys.TinderZoo.model.UserData;
import com.dploveboys.TinderZoo.repositories.InterestRepository;
import com.dploveboys.TinderZoo.repositories.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDataService {
    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    public Optional<UserData> findById(Long id)
    {
        return userDataRepository.findById(id);
    }

    public void addUserData(UserData userData){
        userDataRepository.save(userData);
    }

    /*
    public Queue<Match> getPotentialMatches(UserData our_user) //maybe return List<Long>
    {
        Long our_userId = our_user.getId(); //don't go through interests looking at your own interests
        List<Interest> our_interests = interestRepository.getInterestsByUserId(our_userId);

        //PriorityBlockingQueue used because it's thread safe
        Queue <Match> sorted_matches = new PriorityBlockingQueue<>(); //store possible matches here and change score with each iteration

        int currentScore = 0; //this goes up with each interest we have( for example we have 10 interests, max points a match will have is 10)
        for(Interest interest : our_interests)
        {
            List <Long> possible_matches_id = interestRepository.getUserIDsByInterests(interest.getInterest_tag(), our_userId);

            for(Long possible_match : possible_matches_id)
            {
                Match temp_match = new Match(possible_match);
                temp_match.addScore();

                if(sorted_matches.contains(temp_match)) {
                    sorted_matches.remove(temp_match); //remove old
                    sorted_matches.add(temp_match); //replace with new
                }
            }

            currentScore++;
        }

        System.out.println("Matches gathered so far: " + sorted_matches);

        return sorted_matches;
    }

     */
}
