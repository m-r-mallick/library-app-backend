//package com.project.LibraryManagement.Security.Authentication;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//import java.util.Collection;
//
//public class JwtAuthenticationToken implements Authentication {
//
//    @Autowired
//    UserDetailsService userDetailsService;
//
//    private final String username;
//
//    public JwtAuthenticationToken(String username) {
//        super();
//        this.username = username;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return userDetailsService.loadUserByUsername(username).getAuthorities();
//    }
//
//    @Override
//    public Object getCredentials() {
//        return userDetailsService.loadUserByUsername(username).getPassword();
//    }
//
//    @Override
//    public Object getDetails() {
//        return null;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return userDetailsService.loadUserByUsername(username).getUsername();
//    }
//
//    @Override
//    public boolean isAuthenticated() {
//        return false;
//    }
//
//    @Override
//    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//
//    }
//
//    @Override
//    public String getName() {
//        return null;
//    }
//}
