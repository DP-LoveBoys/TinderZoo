package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.model.Match;

import com.dploveboys.TinderZoo.model.UserData;
import com.dploveboys.TinderZoo.service.PhotoService;
import com.dploveboys.TinderZoo.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class UserController {


    @Autowired
    private UserDataService userDataService;

    @Autowired
    private PhotoService photoService;


    @RequestMapping("/profile_configuration/{userId}")
    public String getProfileConfiguration(@PathVariable("userId") Long userId, Model model){
        UserData user = new UserData();
        model.addAttribute("userId",userId);
        model.addAttribute("user", user);
        return "profile_configuration";
    }

    @PostMapping("/process_profile_data")
    public String addUserData(
            @RequestParam("profile_picture")MultipartFile photo,
            @RequestParam("userId") Long userId,
            @ModelAttribute("user") UserData userData
            ){

        // CKeditor adds paragraph tags around description and must be deleted
        String truncDesc=userData.getDescription();
        truncDesc=truncDesc.substring(3,truncDesc.length()-6);
        userData.setDescription(truncDesc);

        userData.setId(userId);
        String description=userData.getDescription();
        userDataService.addUserData(userData);
        photoService.savePhoto(photo,userId,true);

        return "redirect:/login";
    }

    @RequestMapping("/matches/{userId}")
    public String showMatches(@PathVariable("userId") Long userId, Model model){
        Match pseudo_random_match = new Match();
        //get a match from the UserDataService and display it on matches page -> let user give response -> move to next
        model.addAttribute("match", pseudo_random_match);
        return "matches/" + userId;
    }

    @PostMapping("/matches_processing")
    public String yesOrNoTheMatch(@RequestParam("interest_tag")String response, @RequestParam("userId") Long userId, @RequestParam("other_userId") Long other_userId)
    {
        //do something with the response from the "form" and do something with the accepted or declined match showed
        return "redirect:matches/" + userId;
    }


}
