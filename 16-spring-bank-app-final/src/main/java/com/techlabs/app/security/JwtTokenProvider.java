package com.techlabs.app.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.techlabs.app.exception.StudentApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;
    

    // generate JWT token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        System.out.println("generate token 1");

        Key Key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(Key, SignatureAlgorithm.HS256)
                .compact();
        
        System.out.println("generate token 2");
        
        return token;
    }
    
//    public String generateToken(Authentication authentication) {
//
//    	System.out.println("generate token 2");
//
//        return Jwts
//                .builder()
//                .setSubject(authentication.getName())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 * 90))
//                .signWith(Key(),SignatureAlgorithm.HS256)
//                .compact();
//    }

//    private Key key(){
//        return Keys.hmacShaKeyFor(
//                Decoders.BASE64.decode(jwtSecret)
//        );
//    }
    
//    private Key key() {
//        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
//        System.out.println("Generated Key: " + key);
//        return key;
//    }
    
//    public Key Key(){
//        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

    // get username from Jwt token
    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        return username;
    }

    // validate Jwt token
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new StudentApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new StudentApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new StudentApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new StudentApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
    }
}