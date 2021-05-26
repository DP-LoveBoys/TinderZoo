package com.dploveboys.TinderZoo.controllers;

import com.dploveboys.TinderZoo.model.*;
import com.dploveboys.TinderZoo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserCredentialService userCredentialService;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication: " + authentication);
        if(authentication != null)
        {
            System.out.println("Authenticated: " + authentication.getName());
            String email = authentication.getName();
            Long destId = userCredentialService.getIdByEmail(email);
        }

        http.addFilterBefore(new LoginPageFilter(userCredentialService), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers("/oauth2/**", "/register", "/index").permitAll()
                .antMatchers("/login").not().authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .antMatchers("/images/*").permitAll()
                //.anyRequest().permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .usernameParameter("email")
                    .defaultSuccessUrl("/profile_configuration")
                    .failureUrl("/login.html?error=true")
                    .successHandler(customSuccessHandler)
                    .permitAll() //redirect a successful login to /list_users
                .and()
                .oauth2Login()
                    .userInfoEndpoint().userService(OAuth2UserService)
                    .and()
                    .successHandler(new AuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                            Authentication authentication) throws IOException, ServletException
                        {
                            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
                            String email = oAuth2User.getEmail();

                            UserCredential user = userService.getUserByEmail(email);
                            String name = oAuth2User.getFullName();

                            if(user == null)
                            {
                                userService.registerNewUserAfterOAuthLoginSuccess(email, name, AuthenticationProvider.FACEBOOK);
                            }
                            else
                            {
                                userService.updateExistingUserAfterOAuthLoginSuccess(user, name, AuthenticationProvider.FACEBOOK);
                            }
                            System.out.println("Redirecting");
                            response.sendRedirect("/profile_configuration");
                        }
                    })
                .and()
                .logout()
                    .logoutSuccessUrl("/").permitAll();


    }

    @Autowired
    private CustomOAuth2UserService OAuth2UserService;

    @Autowired
    private OAuth2LoginSuccessHandler successHandler;

    @Autowired
    private UserCredentialService userService;

    @Autowired
    private CustomSuccessHandler customSuccessHandler;
}
