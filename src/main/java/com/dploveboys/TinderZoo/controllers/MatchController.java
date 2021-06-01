package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.*;
import com.dploveboys.TinderZoo.repositories.MatchRepository;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.*;

@Controller
public class MatchController {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private UserCredentialService userCredentialService;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    MatchService matchService;

    @Autowired
    InterestService interestService;

    @Autowired
    private PhotoService photoService;



    @RequestMapping("/pending_matches/{userId}/{preferedDistance}")
    public String getPendingMatchesPage(@PathVariable("userId") Long userId, Double preferedDistance, Model model) throws IOException {
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
        List <Long> people_that_we_like = matchService.getMatches(userId, preferedDistance);//, our_interests); //see which people said MATCH for this userId
        model.addAttribute("people_that_we_like", people_that_we_like);
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

    @RequestMapping("/matches_page/{userId}")
    public String getMyMatches(@PathVariable("userId") Long userId,Model model){
        List<Long> matches=matchService.getConfirmedMatchesIds(userId, MatchResponseProvider.MATCH.toString());
        List<String> names=new ArrayList<>();
        List<Photo> profilePictures=new ArrayList<>();
        Photo profilePicture;

        model.addAttribute("userId",userId);
        model.addAttribute("username",userCredentialService.getUserById(userId).get().getName());
        profilePicture=photoService.getProfilePhoto(userId);
        if(profilePicture==null){
            profilePicture = new Photo();
        }
        model.addAttribute("profilePicture",profilePicture);


        List<Integer> index = new ArrayList<>();

        int i=0;

        for (Long m : matches
        ) {

            names.add(userCredentialService.getUserById(m).get().getName());
            Photo p=photoService.getProfilePhoto(m);
            if(p==null){
                p=new Photo();
            }
            profilePictures.add(p);
            index.add(i);
            i++;
        }

        model.addAttribute("matches",matches);
        model.addAttribute("profilePictures",profilePictures);
        model.addAttribute("names",names);
        model.addAttribute("index",index);

        return "matches";
    }

    @PostMapping("/unmatch")
    public String unmatchUser(@RequestParam("matchId")Long matchId,@RequestParam("userId") Long userId){
        matchService.unmatch(userId,matchId);
        return "redirect:profile/"+userId+"/"+matchId;
    }

    @PostMapping("/addMatch")
    public String addMatch(@RequestParam("matchId") Long matchId,@RequestParam("userId") Long userId){
        matchService.match(userId,matchId);
        return "redirect:profile/"+userId+"/"+matchId;
    }

}
