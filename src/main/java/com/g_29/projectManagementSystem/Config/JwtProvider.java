package com.g_29.projectManagementSystem.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {

   static SecretKey key= Keys.hmacShaKeyFor(JwtConstants.SECREAT_KEY.getBytes());

   public static String generateToken(Authentication authentication){

       String jwt= Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+86400000))
               .claim("email",authentication.getName())
               .signWith(key)
               .compact();

       return jwt;
   }

   public static String getEmailFromToken(String jwt){
       Claims claims=Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

       String email=String.valueOf(claims.get("email"));

       return email;
   }

}
