package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.model.UserData;
import com.dploveboys.TinderZoo.repositories.UserDataRepository;
import com.dploveboys.TinderZoo.service.InterestService;
import com.dploveboys.TinderZoo.service.ProfilePictureService;
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
    private ProfilePictureService profilePictureService;


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

        userData.setId(userId);
        System.out.println(userData);
        userDataService.addUserData(userData);
        //System.out.println("SENT PROFILE PICTURE: "+photo.getOriginalFilename()+"\nAnd user id: "+userId);
        profilePictureService.saveProfilePicture(photo,userId);

        return "redirect:/home/"+userId;
    }

}
