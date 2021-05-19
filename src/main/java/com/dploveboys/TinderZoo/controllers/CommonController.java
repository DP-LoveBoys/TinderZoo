package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @RequestMapping({"/list_usersCredentials"})
    public String viewUserCredentialsList(Model model){

        model.addAttribute("listUsersCredentials", userCredentialRepository.findAll());
        return "list_usersCredentials";
    }

    @RequestMapping("/login") //if you want to go back to login as a logged in user, it takes you back to index
    public String viewLoginPage(Model model){
        model.addAttribute("userCredentials",new UserCredential());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication is " +  authentication);
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken)
        {
            return "login";
        }
        return "redirect:/";
    }
}
