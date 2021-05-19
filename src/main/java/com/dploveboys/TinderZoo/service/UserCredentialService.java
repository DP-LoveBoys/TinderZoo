package com.dploveboys.TinderZoo.service;

import com.dploveboys.TinderZoo.model.AuthenticationProvider;
import com.dploveboys.TinderZoo.model.CustomUserDetails;
import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCredentialService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    public Optional<UserCredential> getUserById(Long userId){
        return userCredentialRepository.findById(userId);
    }

    public UserCredential getUserByEmail(String email) throws UsernameNotFoundException {
        UserCredential userCredential = userCredentialRepository.findByEmail(email);
        /*
        if (userCredential == null) {
            throw new UsernameNotFoundException("The user was not found");
        }
        */

        return userCredential;

    }

    public void registerNewUserAfterOAuthLoginSuccess(String email, String name, AuthenticationProvider authenticationProvider) {
        UserCredential userCredential = new UserCredential();
        userCredential.setEmail(email);
        userCredential.setName(name);
        userCredential.setAuthenticationProvider(authenticationProvider);

        userCredentialRepository.save(userCredential);
    }

    public void updateExistingUserAfterOAuthLoginSuccess(UserCredential userCredential, String name, AuthenticationProvider authenticationProvider)
    {
        userCredential.setName(name);
        userCredential.setAuthenticationProvider(authenticationProvider);
    }
}
