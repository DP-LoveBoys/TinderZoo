package com.dploveboys.TinderZoo.datainit;

import com.dploveboys.TinderZoo.model.User;
import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.repositories.UserRepository;
import com.dploveboys.TinderZoo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// introduce a user into MySQL database to demonstrate working functionality
// uncomment lines below to add a user into database


@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserCredentialRepository userCredentialRepository;
    @Autowired
    private UserService userService;

    public DataInitializer(UserRepository userRepository, UserCredentialRepository userCredentialRepository) {
        this.userRepository = userRepository;
        this.userCredentialRepository = userCredentialRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        //User cutu=new User("Bella","cat","siamese",7,"Romania","Oravita",40, 'M', "blue");
        //userRepository.save(cutu);

        UserCredential userCred = new UserCredential("andrei@andrei.com", "asdasd1212");
        userCredentialRepository.save(userCred);
        System.out.println("New user " + userCred);
    }
}
