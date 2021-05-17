package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.repositories.UserDataRepository;
import com.dploveboys.TinderZoo.service.UserDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {


    private final UserDataRepository userDataRepository;
    private UserDataService userDataService;
    public UserController(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    @RequestMapping("/users")
    public String getUsers(Model model){

        model.addAttribute("users", userDataRepository.findAll());

        return "users/user_list";
    }

    @PostMapping("/process_profile_data")
    public String addUserData(@RequestParam("specie") String specie,@RequestParam("breed") String breed,){

    }

}
