//package com.project.LibraryManagement.Security.Filter;
//
//import com.project.LibraryManagement.Security.Authentication.JwtAuthenticationToken;
//import com.project.LibraryManagement.Security.Services.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    JwtUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain chain) throws ServletException, IOException {
//
//        String initialRequest = request.getHeader("Authorization");
//        if (initialRequest == null || !initialRequest.startsWith("Bearer ")) {
//            response.setHeader("Authorization", "Bearer " + jwtUtil.generateToken(initialRequest));
//            chain.doFilter(request, response);
//            return;
//        }
//
//        String token = initialRequest.substring(7);
//        try {
//            String username = jwtUtil.extractUsername(token);
//            if (jwtUtil.validateToken(token, username)) {
//                var authToken = new JwtAuthenticationToken(username);
//                Authentication authentication = authenticationManager.authenticate(authToken);
//                if (authentication.isAuthenticated()) {
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    chain.doFilter(request, response);
//                }
//            }
//        } catch (Exception e) {
//            throw new BadCredentialsException("Incorrect Credentials!");
//        }
//    }
//}
