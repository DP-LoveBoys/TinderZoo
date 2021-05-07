package com.dploveboys.TinderZoo.datainit;

import com.dploveboys.TinderZoo.model.User;
import com.dploveboys.TinderZoo.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// introduce a user into MySQL database to demonstrate working functionality
// uncomment lines below to add a user into database


@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        //User cutu=new User("Daisy","dog","labrador",10,"Romania","Timisoara",50);
        //userRepository.save(cutu);

        System.out.println("There are "+userRepository.count()+"users.");
    }
}
