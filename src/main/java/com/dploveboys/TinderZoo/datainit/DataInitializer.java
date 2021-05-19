package com.dploveboys.TinderZoo.datainit;

import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.repositories.UserDataRepository;
import com.dploveboys.TinderZoo.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// introduce a user into MySQL database to demonstrate working functionality
// uncomment lines below to add a user into database


@Component
public class DataInitializer implements CommandLineRunner {

    private final UserDataRepository userDataRepository;
    private final UserCredentialRepository userCredentialRepository;

    @Autowired
    private UserDataService userDataService;

    public DataInitializer(UserDataRepository userDataRepository, UserCredentialRepository userCredentialRepository){
        this.userDataRepository = userDataRepository;
        this.userCredentialRepository = userCredentialRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        //User cutu=new User("Bella","cat","siamese",7,"Romania","Oravita",40, 'M', "blue");
        //userRepository.save(cutu);
        UserCredential userCred = new UserCredential("timi@timu.com", "timi", "parola");
       // userCredentialRepository.save(userCred);
        //System.out.println("New user " + userCred);
    }
}
