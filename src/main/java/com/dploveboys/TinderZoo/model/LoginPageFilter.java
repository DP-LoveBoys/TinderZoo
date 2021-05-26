package com.dploveboys.TinderZoo.model;

import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import com.dploveboys.TinderZoo.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
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


        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && ((HttpServletRequest)request).getRequestURI().equals("/login")) {
            System.out.println("user is authenticated but trying to access login page, redirecting to home_page");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("User trying to access login again: " +authentication.getName());

            Long userId = userCredentialService.getIdByEmail(authentication.getName());
            System.out.println("User id: " +userId);
            ((HttpServletResponse)response).sendRedirect("/home_page/" + userId);//authentication.getName());
        }
        chain.doFilter(request, response);
    }

}
