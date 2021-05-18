package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.model.UserData;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.repositories.UserDataRepository;
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

    @GetMapping("/register")
    public String getRegisterPage(Model model)
    {
        model.addAttribute("userCredentials", new UserCredential());
        return "signup";
    }

    @PostMapping("/process_register")
    public String processRegistration(UserCredential userCredential) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = userCredential.getPassword();
        password = encoder.encode(password);
        userCredential.setPassword(password);

        userCredentialRepository.save(userCredential);

        //model.addAttribute("id", userCredential.getId());
        return "redirect:/profile_configuration/"+userCredential.getId();
    }




}
