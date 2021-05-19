package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.service.ProfilePictureService;
import com.dploveboys.TinderZoo.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    UserCredentialService userCredentialService;

    @Autowired
    ProfilePictureService profilePictureService;


    @RequestMapping("/home/{userId}")
    public String getHomePage(@PathVariable("userId") Long userId, Model model){

        model.addAttribute("userId",userId);
        //TODO add all necessary data

        return "/home";
    }

}
