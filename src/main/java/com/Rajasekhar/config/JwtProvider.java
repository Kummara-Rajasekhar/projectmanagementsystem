package com.Rajasekhar.config;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;

public class JwtProvider {

    static SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
   // Claims claims=Jwts.parseBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
   // String email =String.valueOf(claims.get("email"));
    //String authorites=String.valueOf(claims.get("authorities"));

    public static String generateToken(Authentication auth) {

        String jwt=Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email",auth.getName())
                .signWith(key)
                .compact();
        return jwt;

    }

    public static String getEmailFromToken(String jwt){
        Claims claims=Jwts.parseBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String email =String.valueOf(claims.get("email"));
        return email;
    }
}
