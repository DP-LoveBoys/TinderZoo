package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.model.ProfilePicture;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.service.InterestService;
import com.dploveboys.TinderZoo.service.PhotoService;
import com.dploveboys.TinderZoo.service.ProfilePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

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
    private InterestService interestService;

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
        model.addAttribute("userId",userId);
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
