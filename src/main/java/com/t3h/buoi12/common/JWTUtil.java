package com.t3h.buoi12.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.Map;

public class JWTUtil {
    public static String getJWT(String id, Map<String, Object> par) {
        Claims claims = Jwts.claims()
                .setSubject(id);
        claims.setId(id);
        claims.putAll(par);
        claims.put("emailTemp","testing@gmail.com");

        return Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "123a@")
                //set han cua token
                //1 day
                .setExpiration(new Date(new Date().getTime() + 24*60 * 60 * 1000L))
                .compact();
    }

    public static int parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey("123a@")
                .parseClaimsJws(token)
                .getBody();
        int id = Integer.parseInt(claims.getSubject());
//        String email = claims.get("emailTemp", String.class);
//        System.out.println("email: "+email);
        return id;
    }

    public static int getUserLogin(){
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
