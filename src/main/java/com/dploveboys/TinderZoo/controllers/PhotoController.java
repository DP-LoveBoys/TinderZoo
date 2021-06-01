package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.Photo;
import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @RequestMapping("/myphotos/{userId}")
    public String myPhotosPage(@PathVariable("userId") Long userId, Model model){
        Photo profilePicture=photoService.getProfilePhoto(userId);

        model.addAttribute("userId",userId);
        model.addAttribute("photos",photoService.getPhotos(userId));
        Optional<UserCredential> userCredential = userCredentialRepository.findById(userId);

        if(profilePicture==null){
            profilePicture=new Photo();
        }

        try {
            model.addAttribute("username",userCredential.get().getName());
            model.addAttribute("profilePicture",profilePicture);
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }
        return "myphotos";
    }

    @PostMapping("/myphotos")
    public String goToPhotos(@RequestParam("userId") Long userId){
        return "redirect:myphotos/"+userId;
    }

    @PostMapping("/addPhotos")
    public String savePhotos(@RequestParam("photos") MultipartFile[] photos, @RequestParam("userId") Long userId){
        Arrays.asList(photos).stream().forEach(photo -> {
            if(photo.getOriginalFilename().isEmpty()) {
                System.out.println("NU A FOST SELECTATA NICIO IMAGINE");
            }else {
                photoService.savePhoto(photo, userId,false);
            }
        });
        return "redirect:myphotos/"+userId;
    }

    @PostMapping("/deletePhoto")
    public String deletePhoto(@RequestParam("photoId") Long photoId,@RequestParam("userId") Long userId){
        System.out.println("PhotoId="+photoId);
        System.out.println("UserId"+userId);
        photoService.deletePhoto(photoId);
        return "redirect:myphotos/"+userId;
    }

    @PostMapping("/setAsProfile")
    public String setPhotoAsProfile(@RequestParam("photoId") Long photoId,@RequestParam("userId") Long userId){
        photoService.updateProfilePicture(photoId,userId);

        System.out.println("PhotoId="+photoId);
        System.out.println("UserId"+userId);
        return "redirect:myphotos/"+userId;
    }


}
