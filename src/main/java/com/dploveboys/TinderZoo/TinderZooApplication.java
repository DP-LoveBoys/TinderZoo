package com.dploveboys.TinderZoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/*
@ComponentScan( { "it.myapplication.controllers" } )   //added to bypass the spring boot security redirect to login page
@EnableCaching
 */
@SpringBootApplication( exclude = { SecurityAutoConfiguration.class}, //exclude = { SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class}
		scanBasePackages={ "com.dploveboys.TinderZoo"}
						)
public class TinderZooApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinderZooApplication.class, args);
	}

}
