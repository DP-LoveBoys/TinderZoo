package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.ProfilePicture;
import com.dploveboys.TinderZoo.service.PhotoService;
import com.dploveboys.TinderZoo.service.ProfilePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProfileController {

    private Long userId= Long.valueOf(3); // TODO
                          // Replace this hardcoded value and pass the id of the user that is logged in

    @Autowired
    private ProfilePictureService profilePictureService;

    @Autowired
    private PhotoService photoService;

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

    @RequestMapping("/profile")
    public String getUserProfile(Model model){
        model.addAttribute("userId",userId);
        model.addAttribute("profilePicture",profilePictureService.getProfilePicture(userId));
        model.addAttribute("photos",photoService.getPhotos(userId));
        return "/profile";
    }




}
