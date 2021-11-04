package com.project.LibraryManagement.Security.Filter;

import com.project.LibraryManagement.Security.Authentication.CustomAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements Filter {

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        var http = (HttpServletRequest) request;
        String username = http.getHeader("username");
        String password = http.getHeader("password");
        CustomAuthenticationToken authenticationToken = new CustomAuthenticationToken(
                username,
                password,
                new ArrayList<>()
        );
        try {
            Authentication result = authenticationManager.authenticate(authenticationToken);
            if (result.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(result);
                chain.doFilter(request, response);
            }
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Bad Credentials!");
        }
    }
}
