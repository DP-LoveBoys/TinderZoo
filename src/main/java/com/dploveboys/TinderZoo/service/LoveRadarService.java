package com.dploveboys.TinderZoo.service;

import com.dploveboys.TinderZoo.model.Interest;
import com.dploveboys.TinderZoo.model.UserData;
import com.dploveboys.TinderZoo.service.InterestService;
import com.dploveboys.TinderZoo.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;

@ComponentScan(basePackages={"com/dploveboys/TinderZoo/service"})

@Service
public
class LoveRadarService {

    private UserData user;
    //@Resource(name = "interestService")
    @Autowired
    private InterestService interestService;

    @Autowired
    private MatchService matchService;
    private Long id;


    public LoveRadarService(UserData user){
        this.user = user;
    }

    public LoveRadarService() {}

    public Map<Long, Integer> searchForLove()
    {
        Long our_id = user.getId();
        //System.out.println("User is : " + our_id);
        List<Interest> interests = interestService.getInterests(our_id);
        //System.out.println("Interests are: " + interests);

        List <Long> matches = matchService.getMatches(our_id);//, interests);
        //System.out.println("Matches " + matches);

        return null;
    }

    //go through matches here, give MATCH or NOT_INTERESTED
    public void giveMatchResponse() //Long matchId, MatchResponseProvider matchResponseProvider
    {
        Map<Long, Integer> matches; //the matches returned will have a score for each userId (how much in common we have with the matches basically)

        Map<Long, Integer> love_map = this.searchForLove();
        System.out.println("Map is " + love_map);

        /*
        Set<Long> love_list = love_map.keySet();

        if(!love_list.isEmpty())
        {
            System.out.println("Reached giveMatchResponse");
            int i = 0;
            for (Long l : love_list) {
                System.out.println("At lover " + i++ + " " + l);
            }
        }
         */
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}