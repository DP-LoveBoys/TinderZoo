package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.service.CustomOAuth2UserService;
import com.dploveboys.TinderZoo.service.CustomUserDetailsService;
import com.dploveboys.TinderZoo.service.OAuth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService()
    {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception { //This means you have to be logged in to access /list_users, else you can navigate freely
        http.authorizeRequests()
                //.antMatchers("/login").permitAll()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/list_usersCredentials").authenticated() //only need permission to view the full list of users
                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .usernameParameter("email")
                    .defaultSuccessUrl("/list_usersCredentials")
                    .permitAll() //redirect a successful login to /list_users
                .and()
                .oauth2Login()
                    .loginPage("/login")
                    .userInfoEndpoint().userService(OAuth2UserService)
                    .and()
                    .successHandler(successHandler)
                .and()
                .logout().logoutSuccessUrl("/").permitAll();
    }

    @Autowired
    private CustomOAuth2UserService OAuth2UserService;

    @Autowired
    private OAuth2LoginSuccessHandler successHandler;
}
