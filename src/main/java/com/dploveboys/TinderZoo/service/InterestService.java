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
        Interest temp_interest = new Interest(userId, interest);

        interestRepository.save(temp_interest);
    }

    public List<Interest> getInterests(Long userId){
        return interestRepository.findByUserId(userId);
    }

    public void deleteInterest(Long userId, String interest_tag){
        interestRepository.deleteByTag(userId, interest_tag);
    }

}
