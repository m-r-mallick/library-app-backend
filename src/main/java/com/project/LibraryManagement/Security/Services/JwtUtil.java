//package com.project.LibraryManagement.Security.Services;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Component
//public class JwtUtil {
//
//    @Autowired
//    UserDetailsService userDetailsService;
//
//    private final String SECRET_KEY = "secret";
//    private final Long VALIDITY = (long) 1000 * 60 * 60 * 10;
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//    }
//
//    private Boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new java.util.Date());
//    }
//
//    public String generateToken(String username) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userDetails.getUsername());
//    }
//
//    private String createToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + VALIDITY))
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
//    }
//
//    public Boolean validateToken(String token, String username) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//}
