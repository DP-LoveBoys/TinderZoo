package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.Role;
import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CommonController {
    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private UserCredentialService userCredentialService;

    @RequestMapping("/login") //if you want to go back to login as a logged in user, it takes you back to index
    public String viewLoginPage(Model model){
        model.addAttribute("userCredentials", new UserCredential());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("User trying to access login again: " +authentication.getName());

        if(authentication.isAuthenticated() == true)
        {
            return "redirect:/home_page/";  //+userId;
        }

        return "/login";
    }

    @RequestMapping({"/list_users"})
    public String viewUsersList(Model model){
        List<UserCredential> listUsers = userCredentialService.listAll();

        model.addAttribute("listUsersCredentials", listUsers);
        return "users";
    }

    @RequestMapping({"/users/edit/{userId}"})
    public String editUsers(@PathVariable("userId") Long userId, Model model){

        UserCredential userCredential = userCredentialService.get(userId);
        List <Role> roles = userCredentialService.getRoles();

        model.addAttribute("userCredential", userCredential);
        model.addAttribute("roles", roles);

        return "user_form";
    }
}
