package com.dploveboys.TinderZoo.datainit;

import com.dploveboys.TinderZoo.model.User;
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
    @Autowired
    private UserService userService;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        //User cutu=new User("Bella","cat","siamese",7,"Romania","Oravita",40, 'M', "blue");
        //userRepository.save(cutu);

        System.out.println("There are "+userRepository.count()+"users.");

        /*Long id1 = Long.valueOf(1);
        userService.findById(id1).ifPresent(System.out::println);

        Long id2 = Long.valueOf(2);
        userService.findById(id2).ifPresent(System.out::println);*/
    }
}
