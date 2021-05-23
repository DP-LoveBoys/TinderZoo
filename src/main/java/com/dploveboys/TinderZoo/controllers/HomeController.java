package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.ProfilePicture;
import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.model.UserData;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.service.ProfilePictureService;
import com.dploveboys.TinderZoo.service.UserCredentialService;
import com.dploveboys.TinderZoo.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserCredentialService userCredentialService;

    @Autowired
    ProfilePictureService profilePictureService;

    @Autowired
    UserDataService userDataService;

    @RequestMapping("/home_page/{userId}")
    public String getHomePage(@PathVariable("userId") Long userId,Model model){

        Optional<UserCredential> userCredential = userCredentialService.getUserById(userId);
        Optional<UserData> userData=userDataService.findById(userId);
        ProfilePicture profilePicture = profilePictureService.getProfilePicture(userId);

        model.addAttribute("profilePicture",profilePicture);
        try {
            model.addAttribute("username", userCredential.get().getName());
            model.addAttribute("userData", userData.get());
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }
        return "/home";
    }

}
