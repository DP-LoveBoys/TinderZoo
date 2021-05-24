package com.dploveboys.TinderZoo.service;

import com.dploveboys.TinderZoo.model.CustomUserDetails;
import com.dploveboys.TinderZoo.model.UserCredential;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserCredentialService userService;

    private static final Logger LOG = LoggerFactory.getLogger(CustomSuccessHandler.class);
    private final GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //if redirected from some specific url, need to remove the cachedRequest to force use defaultTargetUrl
        final RequestCache requestCache = new HttpSessionRequestCache();
        final SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (!isAdminAuthority(authentication))
        {
            String targetUrl = super.determineTargetUrl(request, response);

            //System.out.println("Authentication is " + authentication.getDetails());  //this gives sessionId
            //System.out.println("Authentication is " + authentication.isAuthenticated());

            String email = authentication.getName();

            UserCredential user = userService.getUserByEmail(email);
            System.out.println("User is " + user);

            if(targetUrl.isEmpty() || targetUrl.equals("/"))
            {
                targetUrl ="home_page/" + user.getId();
            }
            //targetUrl ="home/" + user.getId();

            System.out.println("Request is " + request);
            clearAuthenticationAttributes(request);
            LOG.info("Redirecting user to the following location {} ",targetUrl);

            redirectStrategy.sendRedirect(request, response, targetUrl);
        }
        else{
            // we invalidating the session for the admin user.
            invalidateSession(request, response);
        }
        clearAuthenticationAttributes(request);
    }

    protected void invalidateSession(final HttpServletRequest request, final HttpServletResponse response) throws IOException
    {
        SecurityContextHolder.getContext().setAuthentication(null);
        request.getSession().invalidate();
        redirectStrategy.sendRedirect(request, response, "/admin");
    }

    protected boolean isAdminAuthority(final Authentication authentication)
    {
        return CollectionUtils.isNotEmpty(authentication.getAuthorities())
                && authentication.getAuthorities().contains(adminAuthority);
    }
}
