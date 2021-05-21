package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.model.ProfilePicture;
import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.model.UserData;
import com.dploveboys.TinderZoo.repositories.PreferenceRepository;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.UsesJava8;
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

    private Long userId= Long.valueOf(3); // TODO
                          // Replace this hardcoded value and pass the id of the user that is logged in

    @Autowired
    private ProfilePictureService profilePictureService;

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

    @RequestMapping("/add_profile_picture/{userId}")
    public String getProfileProfile(@PathVariable("userId") Long userId, Model model){

        model.addAttribute("userId",userId);

        return "/add_profile_picture";
    }

    @PostMapping("/add_profile_picture/addProfilePicture")
    public String saveProfilePicture(@RequestParam("profile_picture") MultipartFile photo,@RequestParam("userId") Long userId,Model model){
        if(photo.getOriginalFilename().isEmpty()) {
            System.out.println("NU A FOST SELECTATA NICIO IMAGINE");
            model.addAttribute("userId",userId);
            return "/add_profile_picture";
        }
        else {
            ProfilePicture profilePicture = profilePictureService.getProfilePicture(userId);
            if (profilePicture != null) {
                profilePictureService.updateProfilePicture(profilePicture,photo);
            } else {
                profilePictureService.saveProfilePicture(photo, userId);
            }
            return "redirect:/profile";
        }
    }

    @PostMapping("/myphotos")
    public String goToPhotos(@RequestParam("userId") Long userId,Model model){
        model.addAttribute("userId",userId);
        model.addAttribute("profilePicture",profilePictureService.getProfilePicture(userId));
        model.addAttribute("photos",photoService.getPhotos(userId));

        Optional<UserCredential> userCredential = userCredentialRepository.findById(userId);
        try {
            model.addAttribute("username",userCredential.get().getName());
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }

        return "/myphotos";
    }

    @PostMapping("/addPhotos")
    public String savePhotos(@RequestParam("photos") MultipartFile[] photos,@RequestParam("userId") Long userId){
        Arrays.asList(photos).stream().forEach(photo -> {
            if(photo.getOriginalFilename().isEmpty()) {
                System.out.println("NU A FOST SELECTATA NICIO IMAGINE");
            }else {
                photoService.savePhoto(photo, userId);
            }
        });
        return "redirect:/profile";
    }

    @PostMapping("/deletePhoto")
    public String deletePhoto(@RequestParam("photoId") Long photoId){
        photoService.deletePhoto(photoId);
        return "redirect:/profile";
    }

    @RequestMapping("/profile/{userId}")
    public String getUserProfile(@PathVariable("userId") Long userId, Model model){

        Optional<UserData> userData=userDataService.getUserById(userId);
        Optional<UserCredential> userCredential = userCredentialRepository.findById(userId);

        try {
            model.addAttribute("userData",userData.get());
            model.addAttribute("username",userCredential.get().getName());
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }

        List<Interest> interests=interestService.getInterests(userId);
        model.addAttribute("interests",interests);

        System.out.println(preferenceService.getPreferences(userId));

        model.addAttribute("preferences",preferenceService.getPreferences(userId));
        model.addAttribute("userId", userId);
        model.addAttribute("profilePicture",profilePictureService.getProfilePicture(userId));
        model.addAttribute("photos",photoService.getPhotos(userId));
        return "/profile";
    }

    @PostMapping("/deleteProfile")
    public String deleteProfile(@RequestParam("userId") Long userId){
        userCredentialRepository.deleteProfile(userId);
        return "redirect:/index";
    }

    @RequestMapping("/interests_selection/{userId}")
    public String getInterestsUser(@PathVariable("userId") Long userId, Model model){
        Interest interest = new Interest();

        model.addAttribute("interest", interest);
        return "interests_selection";
    }

    @PostMapping("/interests_selection_processing")
    public String addUserInterest(@RequestParam("interest_tag")String interest_tag, @RequestParam("userId") Long userId, @ModelAttribute("interest") Interest interest)
    {
        interestService.saveInterest(interest_tag, userId);

        return "redirect:interests_selection/" + userId;
    }


}
