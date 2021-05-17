package com.dploveboys.TinderZoo.service;

import com.dploveboys.TinderZoo.model.AuthenticationProvider;
import com.dploveboys.TinderZoo.model.CustomOAuth2User;
import com.dploveboys.TinderZoo.model.UserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserCredentialService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

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

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
