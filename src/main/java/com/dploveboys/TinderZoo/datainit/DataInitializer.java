package com.dploveboys.TinderZoo.datainit;

import com.dploveboys.TinderZoo.model.*;
import com.dploveboys.TinderZoo.repositories.InterestRepository;
import com.dploveboys.TinderZoo.repositories.MatchRepository;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.repositories.UserDataRepository;
import com.dploveboys.TinderZoo.service.InterestService;
import com.dploveboys.TinderZoo.service.MatchService;
import com.dploveboys.TinderZoo.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserDataRepository userDataRepository;
    private final UserCredentialRepository userCredentialRepository;
    private final MatchRepository matchRepository;
    private final InterestRepository interestRepository;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    MatchService matchService;

    @Autowired
    InterestService interestService;

    public DataInitializer(UserDataRepository userDataRepository, UserCredentialRepository userCredentialRepository, MatchRepository matchRepository, InterestRepository interestRepository){
        this.userDataRepository = userDataRepository;
        this.userCredentialRepository = userCredentialRepository;
        this.matchRepository = matchRepository;
        this.interestRepository = interestRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Long userId = 1L;

        //UserCredential temp_cred = new UserCredential("joe@joe.com", "john", "password");
        //UserData temp_user = new UserData(userId, "dog", "joe", 7, "romania", "oravita", 500, 'F', "brown","meow");


    }
}
