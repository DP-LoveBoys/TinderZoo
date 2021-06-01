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

import java.util.*;

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
    PhotoService photoService;

    @RequestMapping("/discover_page/{userId}")
    public String getDiscoverPage(@PathVariable("userId") Long userId, Model model){

        Optional<UserCredential> userCredential = userCredentialService.getUserById(userId);
        UserData userData = userDataService.getUserByIdAsUserDataType(userId);

        ArrayList <Long> actualMatches =  matchService.getMatches(userId);
        ArrayList <Integer> indexes = new ArrayList<>();
        ArrayList <UserData> users_data = new ArrayList<>();
        ArrayList <String> usernames = new ArrayList<>();
        ArrayList <Photo> pictures = new ArrayList<>();

        Optional<UserData> temp_user;
        int index = 0;
        for(Long matchID : actualMatches)
        {
            indexes.add(index);
            System.out.println("MatchID is : " + matchID);

            temp_user = userDataService.getUserById(matchID);

            UserData actualUser;
            if(!temp_user.isPresent())
            {
                 actualUser = new UserData();
            }
            else
            {
                 actualUser = temp_user.get();
            }
            users_data.add(actualUser);
            System.out.println("Actual user is : " + actualUser);

            UserCredential matchcred = userCredentialService.getUserById(matchID).get();

            Photo picture=photoService.getProfilePhoto(actualUser.getId());
            if(picture==null){
                picture = new Photo();
            }

            String username = matchcred.getName();
            if(username == null)
            {
                username = "Anonymous";
            }
            usernames.add(username);
            pictures.add(picture);

            index++;
        }

        model.addAttribute("userId",userId);
        model.addAttribute("matches",actualMatches);
        model.addAttribute("users_data", users_data);
        model.addAttribute("usernames", usernames);
        model.addAttribute("pictures", pictures);
        model.addAttribute("indexes", indexes);

        return "/discover";      //"no_more_people_on_this_site";
    }

    @RequestMapping("/discover_match")
    public String addMatch(@ModelAttribute("userID") Long userID,@RequestParam("userId") long userId)
    {

        return "redirect:home_page/" + userId;
    }

    @RequestMapping("/discover_nomatch")
    public String deleteUnmatch(@ModelAttribute("targetID") UserData target,@RequestParam("userId") long userId){

        return "redirect:home_page/"+userId;
    }
}
