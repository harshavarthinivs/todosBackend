package com.example.todos.config;

import java.security.Key;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
  
  private static final String SECRET_KEY = "5561444A446C7A706A74307A5147746844517037524D6C794933506C75566F36";

  public String extractUsername(String jwt) {
    // TODO Auto-generated method stub
    return null;
  }
  
  private Claims extractAllClaims(String token){
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
