package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.*;
import com.dploveboys.TinderZoo.repositories.PreferenceRepository;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.UsesJava8;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private InterestService interestService;

    @Autowired
    private PreferenceService preferenceService;




    @RequestMapping("/profile/{userId}")
    public String getUserProfile(@PathVariable("userId") Long userId, Model model){

        Optional<UserData> userData=userDataService.getUserById(userId);
        Optional<UserCredential> userCredential = userCredentialRepository.findById(userId);
        List<Interest> interests=interestService.getInterests(userId);
        Photo profilePicture=photoService.getProfilePhoto(userId);

        Preference preferences=preferenceService.getPreferences(userId);
        if(preferences==null) {
            preferences = new Preference();
        }

        if(profilePicture==null){
            profilePicture=new Photo();
        }

        try {
            model.addAttribute("userData",userData.get());
            model.addAttribute("username",userCredential.get().getName());
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }
        model.addAttribute("interests",interests);
        model.addAttribute("preferences", preferences);
        model.addAttribute("userId", userId);
        model.addAttribute("profilePicture", profilePicture);
        model.addAttribute("photos",photoService.getPhotos(userId));
        return "/profile";
    }

    @PostMapping("/deleteProfile")
    public String deleteProfile(@RequestParam("userId") Long userId){
        userCredentialRepository.deleteProfile(userId);
        return "redirect:/index";
    }

    @RequestMapping("/edit_profile/{userId}")
    public String EditProfileUser(@PathVariable("userId") Long userId, Model model){
        UserData user = new UserData();
        Optional<UserData> userData=userDataService.getUserById(userId);

        try {
            model.addAttribute("userData",userData.get());
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }

        model.addAttribute("userId",userId);
        model.addAttribute("user", user);

        return "/edit_profile";
    }

    @RequestMapping("/edit_description/{userId}")
    public String EditDescriptionUser(@PathVariable("userId") Long userId, Model model){
        UserData user = new UserData();
        Optional<UserData> userData=userDataService.getUserById(userId);

        try {
            model.addAttribute("userData",userData.get());
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }

        model.addAttribute("userId",userId);
        model.addAttribute("user", user);

        return "/edit_description";
    }

    @PostMapping("/process_edit_profile_data")
    public String editUserData(@RequestParam("userId") Long userId,@ModelAttribute("user") UserData newUserData){
        newUserData.setId(userId);
        userDataService.updateUserData(userId,newUserData);
        return "redirect:myprofile/"+userId;
    }

    @PostMapping("/edit_user_description")
    public String editUserDescription(@RequestParam("userId") Long userId,@ModelAttribute("user") UserData userData){
        userDataService.updateDescription(userId,userData.getDescription());
        return "redirect:myprofile/"+userId;
    }

    @RequestMapping("/myprofile/{userId}")
    public String getMyProfile(@PathVariable("userId") Long userId, Model model){

        Optional<UserData> userData=userDataService.getUserById(userId);
        Optional<UserCredential> userCredential = userCredentialRepository.findById(userId);
        List<Interest> interests=interestService.getInterests(userId);
        Photo profilePicture=photoService.getProfilePhoto(userId);

        Preference preferences=preferenceService.getPreferences(userId);
        if(preferences==null) {
            preferences = new Preference();
        }

        if(profilePicture==null){
            profilePicture=new Photo();
        }

        try {
            model.addAttribute("userData",userData.get());
            model.addAttribute("username",userCredential.get().getName());
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }
        model.addAttribute("interests",interests);
        model.addAttribute("preferences", preferences);
        model.addAttribute("userId", userId);
        model.addAttribute("profilePicture", profilePicture);
        model.addAttribute("photos",photoService.getPhotos(userId));
        return "/myprofile";
    }

}
