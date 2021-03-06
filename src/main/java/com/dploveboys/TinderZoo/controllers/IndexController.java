package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @RequestMapping({"","/","/index"})
    public String getIndexPage(){
        return "index";
    }

    @RequestMapping("/admin")
    public String getAdminPage(){
        return "/admin";
    }

}
