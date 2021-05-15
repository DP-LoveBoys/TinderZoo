package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.UserData;
import com.dploveboys.TinderZoo.repositories.UserRepository;
import com.dploveboys.TinderZoo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private UserService userService;
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/users")
    public String getUsers(Model model){

        model.addAttribute("users",userRepository.findAll());

        return "users/user_list";
    }


    @RequestMapping("/users/{id}")
    /*
    public String getUser(Long id){
        Optional<User> user = userRepository.findById(id);
        System.out.println("In getUser: " + user);
        if(user.isPresent())
            return user;
        else
        {
          return Optional.empty();
        }

        //model.addAttribute("user",userRepository.findById(id));
        System.out.println("ID is " + id);
        System.out.println("In getUser: " + userRepository.findById(id));
        return "/users/{id}";
    }
    */

    public Optional<UserData> findById(Long id) {


}
