package com.dploveboys.TinderZoo.controllers;

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

        //TODO Ckeditor adds paragraph tags around the description; truncate the description

        userData.setId(userId);
        String description=userData.getDescription();
        userDataService.addUserData(userData);
        photoService.savePhoto(photo,userId,true);

        return "redirect:/login";
    }

}
