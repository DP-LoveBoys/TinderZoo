package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.*;
import com.dploveboys.TinderZoo.repositories.PreferenceRepository;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.service.*;
import com.google.maps.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.UsesJava8;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private MatchService matchService;

    @Autowired
    private LocationService locationService;

    @PostMapping("/view_profile")
    public String sendToProfile(@RequestParam("userId") Long userId,@RequestParam("myId") Long myId){
        return "redirect:profile/"+myId+"/"+userId;
    }

    @RequestMapping("/profile/{userId}/{otherId}")
    public String getUserProfile(@PathVariable("userId") Long userId,@PathVariable("otherId") Long otherId, Model model){

        Optional<UserData> userData=userDataService.getUserById(otherId);
        Optional<UserCredential> userCredential = userCredentialRepository.findById(otherId);
        List<Interest> interests=interestService.getInterests(otherId);
        Photo profilePicture=photoService.getProfilePhoto(otherId);

        Boolean matched;

        Match match=matchService.getMatch(userId,otherId);
        if(match == null)
        {
            match = matchService.getMatch(otherId, userId);
            if(match == null)
            {
                matched = false;
            }
            else
            {
                if(match.getMatchResponseProvider().equals(MatchResponseProvider.MATCH) && match.getUserResponseProvider().equals(MatchResponseProvider.MATCH)){
                    matched=true;
                }
                else{
                    matched=false;
                }
            }
        }
        else
        {
            if(match.getMatchResponseProvider().equals(MatchResponseProvider.MATCH) && match.getUserResponseProvider().equals(MatchResponseProvider.MATCH)){
                matched=true;
            }
            else{
                matched=false;
            }
        }


        Preference preferences=preferenceService.getPreferences(otherId);
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
        model.addAttribute("photos",photoService.getPhotos(otherId));
        model.addAttribute("matched",matched);
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
    public String editUserData(@RequestParam("userId") Long userId,@ModelAttribute("user") UserData newUserData) throws InterruptedException, ApiException, IOException {
        newUserData.setId(userId);
        userDataService.updateUserData(userId,newUserData);

        String address = newUserData.getAddress();
        String country = newUserData.getCountry();
        String city = newUserData.getCity();

        Geocoder geocoder = Geocoder.getInstance();
        Location location = geocoder.GeocodeSync(address + ", " + country + ", " + city);

        locationService.saveUserLocation(userId, location.getLatitude(), location.getLongitude());
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
