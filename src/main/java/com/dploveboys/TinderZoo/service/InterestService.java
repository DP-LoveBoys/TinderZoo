package com.dploveboys.TinderZoo.service;

import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.repositories.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("interestService")
public class InterestService {

    @Autowired
    private InterestRepository interestRepository;

    public void saveInterest(String interest, Long userId){
        Interest temp_interest = new Interest();
        temp_interest.setUser_id(userId);
        temp_interest.setInterest_tag(interest);

        interestRepository.save(temp_interest);
    }

    public List<Interest> getInterests(Long userId){

        //System.out.println(interestRepository.findById(userId));
        List<Interest> temp = interestRepository.getInterestsByUserId(userId);

        //for(Interest i : temp)
        //    System.out.println(i);

        return temp;
    }

    public void deleteInterest(Long userId, String interest_tag){
        interestRepository.deleteByTag(userId, interest_tag);
    }


    public List<Long> getUsers(String interest, Long exceptId) {
        return interestRepository.getUserIDsByInterests(interest, exceptId);
    }

    public void deleteInterestById(Long interestId){
        Interest interest=interestRepository.findById(interestId).get();
        interestRepository.delete(interest);
    }

    public List<Long> getUsersExceptThisId(String interest, Long thisId)
    {
        return interestRepository.getUserIDsByInterestsButNotThisUser(interest, thisId);
    }

}
