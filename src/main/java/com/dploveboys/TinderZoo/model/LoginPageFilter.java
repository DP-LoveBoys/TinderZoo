package com.dploveboys.TinderZoo.model;

import com.dploveboys.TinderZoo.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginPageFilter extends GenericFilterBean {

    @Autowired
    UserCredentialService userCredentialService;

    public LoginPageFilter(UserCredentialService userCredentialService) {
        this.userCredentialService = userCredentialService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        /*
        if (SecurityContextHolder.getContext().getAuthentication() == null
                && ((HttpServletRequest)request).getRequestURI().equals("/register"))
        {
            System.out.println("user is trying to access register page, redirecting to process_register");


            //((HttpServletResponse)response).sendRedirect("/register");  ///process_register
        }
        else
         */
         if (SecurityContextHolder.getContext().getAuthentication() != null

                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && ((HttpServletRequest)request).getRequestURI().equals("/login"))
        {
            System.out.println("user is authenticated but trying to access login page, redirecting to home_page");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("User trying to access login again: " +authentication.getName());

            Long userId = userCredentialService.getIdByEmail(authentication.getName());

            ((HttpServletResponse)response).sendRedirect("/home_page/" + userId);//authentication.getName());
        }

        chain.doFilter(request, response);
    }

}
