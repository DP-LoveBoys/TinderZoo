package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.Photo;
import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.model.UserData;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.service.PhotoService;
import com.dploveboys.TinderZoo.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class NotificationController {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @RequestMapping("/notifications/{userId}")
    public String getPendingNotificationPage(@PathVariable("userId") Long userId, Model model){
        Optional<UserData> userData=userDataService.getUserById(userId);
        UserData user = new UserData();
        Optional<UserCredential> userCredential = userCredentialRepository.findById(userId);

        Photo profilePicture=photoService.getProfilePhoto(userId);
        if(profilePicture==null){
            profilePicture=new Photo();
        }
        try {
            model.addAttribute("userData",userData.get());
            model.addAttribute("username",userCredential.get().getName());
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }


        model.addAttribute("profilePicture", profilePicture);
        model.addAttribute("userId",userId);
        model.addAttribute("user", user);

        return "/notifications";
    }
}
