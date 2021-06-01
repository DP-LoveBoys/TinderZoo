package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class InterestController {

    @Autowired
    private InterestService interestService;

    @RequestMapping("/interests_selection/{userId}")
    public String getInterestsUser(@PathVariable("userId") Long userId, Model model){
        Interest interest = new Interest();

        List<Interest> interests=interestService.getInterests(userId);
        model.addAttribute("interests",interests);
        model.addAttribute("userId", userId);
        model.addAttribute("interest", interest);
        return "interests_selection";
    }

    @PostMapping("/interests_selection_processing")
    public String addUserInterest(@RequestParam("interest_tag")String interest_tag, @RequestParam("userId") Long userId, @ModelAttribute("interest") Interest interest)
    {
        interestService.saveInterest(interest_tag, userId);

        return "redirect:interests_selection/" + userId;
    }

    @PostMapping("/deleteInterest")
    public String deleteInterest(@RequestParam("interestId") Long interestId,@RequestParam("userId") Long userId){
        interestService.deleteInterestById(interestId);
        return "redirect:interests_selection/"+userId;
    }

}
