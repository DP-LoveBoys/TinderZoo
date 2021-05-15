package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.ProfilePicture;
import com.dploveboys.TinderZoo.service.ProfilePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProfileController {

    private Long userId= Long.valueOf(4); // TODO
                          // Replace this hardcoded value and pass the id of the user that is logged in

    @Autowired
    private ProfilePictureService profilePictureService;

    @RequestMapping("/add_profile_picture")
    public String getProfileProfile(Model model){

        model.addAttribute("user_id",userId);

        return "/add_profile_picture";
    }

    @PostMapping("/add_picture")
    public String saveProfilePicture(@RequestParam("profile_picture") MultipartFile photo,@RequestParam("userId") Long userId,Model model){
        profilePictureService.saveProfilePicture(photo,userId);
        return "redirect:/profile";
    }

    @RequestMapping("/profile")
    public String getUserProfile(Model model){
        model.addAttribute("profile",profilePictureService.getProfilePicture(userId));
        return "/profile";
    }

}
