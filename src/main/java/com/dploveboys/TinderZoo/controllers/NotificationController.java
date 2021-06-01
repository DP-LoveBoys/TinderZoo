package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.Notification;
import com.dploveboys.TinderZoo.model.Photo;
import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.model.UserData;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.service.NotificationService;
import com.dploveboys.TinderZoo.service.PhotoService;
import com.dploveboys.TinderZoo.service.UserCredentialService;
import com.dploveboys.TinderZoo.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class NotificationController {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private UserCredentialService userCredentialService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/notifications/{userId}")
    public String getPendingNotificationPage(@PathVariable("userId") Long userId, Model model){
        List<Long> users = new ArrayList<>();
        List<String> names=new ArrayList<>();
        List<Photo> profilePictures=new ArrayList<>();
        Photo profilePicture;

        model.addAttribute("userId",userId);
        model.addAttribute("username",userCredentialService.getUserById(userId).get().getName());

        profilePicture=photoService.getProfilePhoto(userId);
        if(profilePicture==null){
            profilePicture = new Photo();
        }
        model.addAttribute("profilePicture",profilePicture);


        // now add the other users

        List<Notification> notifications= notificationService.getNotifications(userId);
        List<Integer> index = new ArrayList<>();

        int i=0;

        for (Notification n : notifications
             ) {
            users.add(n.getPretendentId());
            names.add(userCredentialService.getUserById(n.getPretendentId()).get().getName());
            Photo p=photoService.getProfilePhoto(n.getPretendentId());
            if(p==null){
                p=new Photo();
            }
            profilePictures.add(p);
            index.add(i);
            i++;
        }

        model.addAttribute("users",users);
        model.addAttribute("profilePictures",profilePictures);
        model.addAttribute("names",names);
        model.addAttribute("notifications",notifications);
        model.addAttribute("index",index);

        return "notifications";
    }

    @PostMapping("/deleteNotification")
    public String deleteNotification(@RequestParam("notificationId") Long notificationId,@RequestParam("userId") Long userId){
        notificationService.deleteNotification(notificationId);
        return "redirect:notifications/"+userId;
    }




}
