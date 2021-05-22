package com.dploveboys.TinderZoo.datainit;

import com.dploveboys.TinderZoo.model.Match;
import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.repositories.MatchRepository;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.repositories.UserDataRepository;
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

    @Autowired
    private UserDataService userDataService;

    @Autowired
    MatchService matchService;

    public DataInitializer(UserDataRepository userDataRepository, UserCredentialRepository userCredentialRepository, MatchRepository matchRepository){
        this.userDataRepository = userDataRepository;
        this.userCredentialRepository = userCredentialRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        //User cutu=new User("Bella","cat","siamese",7,"Romania","Oravita",40, 'M', "blue");
        //userRepository.save(cutu);
        UserCredential userCred = new UserCredential("timi@timu.com", "timi", "parola");
        //userCredentialRepository.save(userCred);
        System.out.println("New user " + userCred);


        Long userId = Long.valueOf(3);
        Long matchId = Long.valueOf(4);
        //matchRepository.save(new Match(userId, matchId));

        System.out.println("Matches " + matchService.getMatchesByUserId(userId));
        System.out.println("Users " + matchService.getUserIDsByMatchId(matchId));

    }
}
