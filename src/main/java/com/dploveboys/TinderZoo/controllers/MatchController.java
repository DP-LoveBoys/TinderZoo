package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.model.Match;
import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.model.UserData;
import com.dploveboys.TinderZoo.repositories.MatchRepository;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.service.InterestService;
import com.dploveboys.TinderZoo.service.MatchService;
import com.dploveboys.TinderZoo.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class MatchController {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    MatchService matchService;

    @Autowired
    InterestService interestService;

    @RequestMapping("/pending_matches/{userId}")
    public String getPendingMatchesPage(@PathVariable("userId") Long userId, Model model){
        Optional<UserData> userData=userDataService.getUserById(userId);
        UserData user = new UserData();
        Optional<UserCredential> userCredential = userCredentialRepository.findById(userId);

        try {
            model.addAttribute("userData",userData.get());
            model.addAttribute("username",userCredential.get().getName());
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }

        model.addAttribute("userId",userId);
        model.addAttribute("user", user);

        List<Interest> our_interests = interestService.getInterests(userId);
        Map<Long, Integer> people_that_we_like = matchService.getMatches(userId, our_interests); //see which people said MATCH for this userId
        model.addAttribute("people_that_we_like", people_that_we_like.keySet());
        System.out.println("We have people_that_we_like: " + people_that_we_like);

        List<Long> people_that_like_us = matchService.getMatchesByUserId(userId, "MATCH"); //see which people said MATCH for this userId
        model.addAttribute("people_that_like_us", people_that_like_us);
        System.out.println("We have people_that_like_us: " + people_that_like_us);

        List<Long> potential_matches = matchService.getUsersByMatchId(userId, "MATCH"); //see which people said MATCH for this userId
        model.addAttribute("potential_matches", potential_matches);

        System.out.println("We have these users pending approval: " + potential_matches);

        Long our_id = userId;
        for(Long their_id : potential_matches) {
            Match temp_match = matchRepository.getPair(their_id, our_id); //if there already exists this combination stored (example userid = 1, matchId = 2, but you want to store 2 and 1 in this order, which is the same as the first time you stored it
            if (temp_match != null) continue;
            else
            {
                temp_match = new Match(our_id, their_id);
                matchRepository.save(temp_match);
            }
        }
        return "/pending_matches";
    }

    @PostMapping
    public String sendResponseToMatchesPage(@PathVariable("userId") Long userId, Model model){
        Optional<UserData> userData=userDataService.getUserById(userId);
        UserData user = new UserData();
        Optional<UserCredential> userCredential = userCredentialRepository.findById(userId);

        try {
            model.addAttribute("userData",userData.get());
            model.addAttribute("username",userCredential.get().getName());
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }

        model.addAttribute("userId",userId);
        model.addAttribute("user", user);

        List<Interest> our_interests = interestService.getInterests(userId);

        Map<Long, Integer> people_that_we_like = matchService.getMatches(userId, our_interests); //see which people said MATCH for this userId
        model.addAttribute("people_that_we_like", people_that_we_like.keySet());

        List<Long> people_that_like_us = matchService.getUsersByMatchId(userId, "MATCH"); //see which people said MATCH for this userId
        model.addAttribute("people_that_like_us", people_that_like_us);

        System.out.println("We have these users pending approval: " + people_that_like_us);
        return "/pending_matches";
    }
}
