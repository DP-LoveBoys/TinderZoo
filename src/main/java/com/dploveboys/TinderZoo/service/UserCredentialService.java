package com.dploveboys.TinderZoo.service;

import com.dploveboys.TinderZoo.model.AuthenticationProvider;
import com.dploveboys.TinderZoo.model.CustomUserDetails;
import com.dploveboys.TinderZoo.model.Role;
import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.repositories.RoleRepository;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCredentialService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Optional<UserCredential> getUserById(Long userId){
        return userCredentialRepository.findById(userId);
    }

    public UserCredential getUserByEmail(String email) throws UsernameNotFoundException {
        UserCredential userCredential = userCredentialRepository.findByEmail(email);
        /* if (userCredential == null) {
            throw new UsernameNotFoundException("The user was not found");
        }*/
        return userCredential;

    }


    public void saveUserWithDefaultRole(UserCredential userCredential)
    {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encoded = bCryptPasswordEncoder.encode(userCredential.getPassword());
        userCredential.setPassword(encoded);

        Role roleUser = roleRepository.findByName("User");
        userCredential.addRole(roleUser);

        userCredentialRepository.save(userCredential);
    }

    public UserCredential get(Long id)
    {
        return userCredentialRepository.findById(id).get();
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

    public List<UserCredential> listAll()
    {
        return (List<UserCredential>) userCredentialRepository.findAll();
    }

    public List<Role> getRoles()
    {
        return (List<Role>) roleRepository.findAll();
    }
}
