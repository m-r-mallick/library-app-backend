//package com.project.LibraryManagement.Security.Provider;
//
//import com.project.LibraryManagement.Security.Authentication.CustomAuthenticationToken;
//import com.project.LibraryManagement.Security.Authentication.JwtAuthenticationToken;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JwtAuthenticationProvider implements AuthenticationProvider {
//
//    @Autowired
//    UserDetailsService userDetailsService;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//        String username = String.valueOf(authentication.getPrincipal());
//        String password = String.valueOf(authentication.getCredentials());
//        UserDetails user = userDetailsService.loadUserByUsername(username);
//        if (user != null) {
//            if (passwordEncoder.matches(password, user.getPassword())) {
//                return new CustomAuthenticationToken(
//                        username,
//                        password,
//                        user.getAuthorities()
//                );
//            }
//        }
//        throw new BadCredentialsException("Incorrect Credentials!");
//    }
//
//    @Override
//    public boolean supports(Class<?> authType) {
//        return JwtAuthenticationToken.class.equals(authType);
//    }
//}
