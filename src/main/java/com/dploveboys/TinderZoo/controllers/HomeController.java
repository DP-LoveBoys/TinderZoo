package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.Photo;
import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.model.UserData;
import com.dploveboys.TinderZoo.service.NotificationService;
import com.dploveboys.TinderZoo.service.PhotoService;
import com.dploveboys.TinderZoo.service.UserCredentialService;
import com.dploveboys.TinderZoo.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserCredentialService userCredentialService;

    @Autowired
    PhotoService photoService;

    @Autowired
    UserDataService userDataService;

    @Autowired
    NotificationService notificationService;

    @RequestMapping("home_page/{userId}") //id/
    public String getHomePage(@PathVariable("userId") Long userId,Model model){

        int notifications=notificationService.getNotificationCount(userId);
        Optional<UserCredential> userCredential = userCredentialService.getUserById(userId);
        Optional<UserData> userData=userDataService.getUserById(userId);

        Photo profilePicture =photoService.getProfilePhoto(userId);
        if(profilePicture==null){
            profilePicture=new Photo();
        }

        if(!userData.isPresent()){
            System.out.println("Hei");
            return "redirect:/profile_configuration/"+userId;
        }

        model.addAttribute("profilePicture",profilePicture);
        try {
            model.addAttribute("username", userCredential.get().getName());
            model.addAttribute("userData", userData.get());
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }

        model.addAttribute("notifications",notifications);

        return "home";
    }
/*
    @RequestMapping("/home_page/{userName}") // name/
    public String getHomePage2(@RequestParam("userName") String userName, Model model){

        Long userId = userCredentialService.getIdByEmail(userName);

        Optional<UserCredential> userCredential = userCredentialService.getUserById(userId);
        Optional<UserData> userData=userDataService.getUserById(userId);

        Photo profilePicture =photoService.getProfilePhoto(userId);
        if(profilePicture==null){
            profilePicture=new Photo();
        }
        System.out.println(userData + "Andrei");


        model.addAttribute("profilePicture",profilePicture);
        try {
            model.addAttribute("username", userCredential.get().getName());
            model.addAttribute("userData", userData.get());
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }

        return "home";
    }


 */
}
