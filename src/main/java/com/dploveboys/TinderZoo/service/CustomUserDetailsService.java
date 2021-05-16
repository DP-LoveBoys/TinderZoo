package com.dploveboys.TinderZoo.service;

import com.dploveboys.TinderZoo.model.CustomUserDetails;
import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserCredential userCredential = userCredentialRepository.findByEmail(email);
        System.out.println("User is: " + userCredential);
        if(userCredential == null)
        {
            throw new UsernameNotFoundException("The user was not found");
        }
        return new CustomUserDetails(userCredential);

    }
}
