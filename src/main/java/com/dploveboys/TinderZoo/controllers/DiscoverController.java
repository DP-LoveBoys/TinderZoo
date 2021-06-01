package com.dploveboys.TinderZoo.controllers;


import com.dploveboys.TinderZoo.model.*;

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

    @Autowired
    LocationService locationService;

    @RequestMapping("/discover_page/{userId}/{preferedDistance}")
    public String getDiscoverPage(@PathVariable("userId") Long userId, Double preferedDistance, Model model){

        Optional<UserCredential> userCredential = userCredentialService.getUserById(userId);
        UserData userData = userDataService.getUserByIdAsUserDataType(userId);

        ArrayList <Long> actualMatches =  matchService.getMatches(userId, preferedDistance);

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

        return "discover";      //"no_more_people_on_this_site";
    }

    @RequestMapping("/discover_match")
    public String addMatch(@ModelAttribute("userID") Long userID,@RequestParam("userId") long userId)
    {

        return "redirect:/home_page/" + userId;
    }

    @RequestMapping("/discover_nomatch")
    public String deleteUnmatch(@ModelAttribute("targetID") UserData target,@RequestParam("userId") long userId){

        return "redirect:/home_page/" + userId;
    }

    @RequestMapping("/configure_distance/{userId}")
    public String configureDistance(@PathVariable("userId") Long userId,Model model){
        UserData userData=userDataService.getUserById(userId).get();
        if(userData==null){
            userData=new UserData();
        }

        model.addAttribute("userData",userData);
        model.addAttribute("userId",userId);

        return "/configure_distance";
    }

    @PostMapping("/distancePreference")
    public String setDistanceParams(@RequestParam("userId") Long userId,
                                    @RequestParam("location")String location,
                                    @RequestParam("address") String address,
                                    @RequestParam("preferedDistance") Double preferedDistance
                                    ){

        System.out.println("Address: "+address);
        System.out.println("Location is " + location);
        String[] splitString = location.split(","); //[0-9.]
        int i = 0;
        String latitude = null;
        String longitude = null;
        System.out.println("Split string is ");
        for(String s : splitString){
            System.out.println(s);
            if(i == 0)
                latitude = s.substring(1);
            else
                longitude = s.substring(0, 18);
            i++;
        }
        System.out.println("latitude string is " + latitude);
        System.out.println("longitude string is " + longitude);


        //locationService.saveUserLocation(userId, Double.valueOf(latitude), Double.valueOf(longitude));


        return "redirect:/discover_page/"+userId + "/" + preferedDistance;
    }

}
