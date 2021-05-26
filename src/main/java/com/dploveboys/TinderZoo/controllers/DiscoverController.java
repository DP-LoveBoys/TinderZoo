package com.dploveboys.TinderZoo.controllers;


import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.model.Photo;
import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.model.UserData;

import com.dploveboys.TinderZoo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class DiscoverController {
    @Autowired
    UserDataService userDataService;

    @Autowired
    UserCredentialService userCredentialService;

    @Autowired
    InterestService interestService;

    @Autowired
    MatchService matchService;

    @Autowired
    PhotoService PhotoService;

    @RequestMapping("/discover_page/{userId}")
    public String getDiscoverPage(@PathVariable("userId") Long userId, Model model){



        Optional<UserCredential> userCredential = userCredentialService.getUserById(userId);
        Optional<UserData> userData=userDataService.getUserById(userId);
        Map<Long, Integer> matches; //the matches returned will have a score for each userId (how much in common we have with the matches basically)

        Map<Long, Integer> love_map;
        UserData temp_user = new UserData(userId, "dog", "joe", 7, "romania", "oravita", 500, 'F', "brown","meow");
        Long our_id = userCredential.get().getId();
        System.out.println("User is : " + our_id);
        UserCredential matchcred = userCredentialService.getUserById(temp_user.getId()).get();
        Photo picture=PhotoService.getProfilePhoto(temp_user.getId());
        try {
            model.addAttribute("match_name",matchcred.getName());
            model.addAttribute("username", userCredential.get().getName());
            model.addAttribute("userData", temp_user);
            model.addAttribute("UserID",our_id);
            model.addAttribute("profilePicture",picture);
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }
        return "/discover";
    }
    @RequestMapping("/discover_match")
    public String addMatch(@ModelAttribute("userID") Long userID,@RequestParam("userId") long userId)
    {

        return "redirect:/home_page/" + userId;
    }

    @RequestMapping("/discover_nomatch")
    public String deleteUnmatch(@ModelAttribute("targetID") UserData target,@RequestParam("userId") long userId){

        return "redirect:/home_page/"+userId;
    }
}
