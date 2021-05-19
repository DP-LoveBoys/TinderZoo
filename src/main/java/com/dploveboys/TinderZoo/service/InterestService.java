package com.dploveboys.TinderZoo.service;

import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.repositories.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestService {

    @Autowired
    private InterestRepository interestRepository;

    public void saveInterest(String interest, Long userId){
        //System.out.println("User id in InterestService: " + userId);
        Interest temp_interest = new Interest();
        temp_interest.setUser_id(userId);
        temp_interest.setInterest_tag(interest);
        //System.out.println("New interesent generated in InterestService: " + temp_interest);
        interestRepository.save(temp_interest);
    }

    public List<Interest> getInterests(Long userId){
        return interestRepository.getInterestsByUserId(userId);
    }

    public void deleteInterest(Long userId, String interest_tag){
        interestRepository.deleteByTag(userId, interest_tag);
    }

}
