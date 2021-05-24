package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.Preference;
import com.dploveboys.TinderZoo.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;

    @PostMapping("/preferences_selection_processing")
    public String editPreferences(@RequestParam("userId") Long userId, @RequestParam("preference") String preference){
        preferenceService.addPreference(userId,preference);
        return "redirect:preferences_selection/"+userId;
    }

    @PostMapping("/deletePreference")
    public String deletePreference(@RequestParam("userId") Long userId,@RequestParam("preference") String preference){
        preferenceService.deletePreference(userId,preference);
        return "redirect:preferences_selection/"+userId;
    }

    @RequestMapping("/preferences_selection/{userId}")
    public String getPreferencesUser(@PathVariable("userId") Long userId, Model model){
        Preference preference = new Preference();

        Preference preferences=preferenceService.getPreferences(userId);

        if(preferences==null) {
            preferences = new Preference();
        }
        model.addAttribute("preferences",preferences);
        model.addAttribute("userId", userId);
        return "/preferences_selection";
    }
}
