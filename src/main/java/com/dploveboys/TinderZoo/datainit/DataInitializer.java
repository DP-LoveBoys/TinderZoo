package com.dploveboys.TinderZoo.datainit;

import com.dploveboys.TinderZoo.model.*;
import com.dploveboys.TinderZoo.repositories.InterestRepository;
import com.dploveboys.TinderZoo.repositories.MatchRepository;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.repositories.UserDataRepository;
import com.dploveboys.TinderZoo.service.InterestService;
import com.dploveboys.TinderZoo.service.LoveRadarService;
import com.dploveboys.TinderZoo.service.MatchService;
import com.dploveboys.TinderZoo.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

        UserCredential temp_cred = new UserCredential("joe@joe.com", "john", "password");
        UserData temp_user = new UserData(userId, "dog", "joe", 7, "romania", "oravita", 500, 'F', "brown","meow");

        LoveRadarService loveRadar = new LoveRadarService(temp_user);
        //loveRadar.giveMatchResponse();
        //------------------------------------------------

        Map<Long, Integer> matches; //the matches returned will have a score for each userId (how much in common we have with the matches basically)
        Map<Long, Integer> love_map;

        Long our_id = temp_user.getId();
        Long their_id = 2L;
        //System.out.println("User is : " + our_id);

        List<Interest> interests = interestService.getInterests(our_id);
        //System.out.println("Interests are: " + interests);

        matches = matchService.getMatches(our_id, interests);  //this will be used to get the potential matches
        //System.out.println("Matches " + matches);

        //EXAMPLE OF SAVING THE MATCHES FOR AN USER TO matches TABLE
        //We have our_id, match_id, match_response_provider
        Set<Long> love_list = matches.keySet(); //for each match the algorithm finds, you will add them to matches table with UNKNOWN response
        if(!love_list.isEmpty())
        {
            MatchResponseProvider our_response = MatchResponseProvider.NOT_INTERESTED;
            MatchResponseProvider match_response = MatchResponseProvider.MATCH;
            for (Long l : love_list)
            {
                //System.out.println("At lover with ID " + l);

                Match temp_match = matchRepository.getPair(our_id, their_id);
                if(temp_match != null)          //the pair exists already, don't add it
                {
                    //matchRepository.updateOurResponse(our_id, their_id, our_response.toString());
                }
                else
                {
                    temp_match = new Match(our_id, l);
                    //matchRepository.save(temp_match);         //UNCOMMENT TO SAVE MATCH
                }
            }
        }

        //CHECK IF FOR THIS ID, WE HAVE MATCHES. IF SOMEONE MATCHED THIS ID, GIVE THIS ID THE OPTION TO GIVE A RESPONSE TO EACH
        //the people that matched us should have said "MATCH" for our id in the table


    }
}
