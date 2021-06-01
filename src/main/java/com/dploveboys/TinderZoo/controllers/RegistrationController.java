package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.model.UserData;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.repositories.UserDataRepository;
import com.dploveboys.TinderZoo.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    private UserDataRepository userDataRepository;

    @RequestMapping("/register")
    public String getRegisterPage(Model model)
    {
        model.addAttribute("userCredentials", new UserCredential());
        //return "redirect:/pre_signup";
        return "signup";
    }
/*
    @RequestMapping("/pre_signup")
    public String getPreSignupPage(Model model)
    {
        model.addAttribute("userCredentials", new UserCredential());
        return "signup";
    }

 */

    @Autowired
    UserCredentialService userCredentialService;
    @PostMapping("/process_register")
    public String processRegistration(UserCredential userCredential) {

        userCredentialService.saveUserWithDefaultRole(userCredential);
        return "redirect:profile_configuration/"+userCredential.getId();
    }




}
