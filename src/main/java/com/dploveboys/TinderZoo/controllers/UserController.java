package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.*;

import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.service.Geocoder;
import com.dploveboys.TinderZoo.service.LocationService;
import com.dploveboys.TinderZoo.service.PhotoService;
import com.dploveboys.TinderZoo.service.UserDataService;
import com.google.maps.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;


@Controller
public class UserController {


    @Autowired
    private UserDataService userDataService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private LocationService locationService;

    @RequestMapping("/profile_configuration/{userId}")
    public String getProfileConfiguration(@PathVariable("userId") Long userId, Model model){
        UserData user = new UserData();
        model.addAttribute("userId",userId);
        model.addAttribute("user", user);
        return "profile_configuration";
    }

    @PostMapping("/process_profile_data")
    public String addUserData(
            @RequestParam("profile_picture")MultipartFile photo,
            @RequestParam("userId") Long userId,
            @ModelAttribute("user") UserData userData
            ) throws InterruptedException, ApiException, IOException {

        // CKeditor adds paragraph tags around description and must be deleted
        String truncDesc=userData.getDescription();
        truncDesc=truncDesc.substring(3,truncDesc.length()-6);
        userData.setDescription(truncDesc);

        userData.setId(userId);
        String description=userData.getDescription();
        String address = userData.getAddress();
        String country = userData.getCountry();
        String city = userData.getCity();

        Geocoder geocoder = Geocoder.getInstance();
        Location location = geocoder.GeocodeSync(address + ", " + country + ", " + city);

        locationService.saveUserLocation(userId, location.getLatitude(), location.getLongitude());

        userDataService.addUserData(userData);
        photoService.savePhoto(photo,userId,true);

        return "redirect:/login";
    }

    @RequestMapping("/matches/{userId}")
    public String getMatchesPage(@PathVariable("userId") Long userId, Model model){
        Match pseudo_random_match = new Match();
        //get a match from the UserDataService and display it on matches page -> let user give response -> move to next
        model.addAttribute("match", pseudo_random_match);

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

        return "matches";
    }

    @PostMapping("/matches_processing")
    public String yesOrNoTheMatch(@RequestParam("interest_tag")String response, @RequestParam("userId") Long userId, @RequestParam("other_userId") Long other_userId)
    {
        //do something with the response from the "form" and do something with the accepted or declined match showed
        return "redirect:matches/" + userId;
    }


}
