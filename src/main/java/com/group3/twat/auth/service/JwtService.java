package com.group3.twat.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "ecefdf9bee95eeb8d70662059034c1e96f4c7be773d950248a02822cd7877527";

    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return  generateToken(new HashMap<>(),userDetails);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username =extractUsername(token);
        return (username.equals(userDetails.getUsername()))&& !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public String generateToken(
            Map<String,Object> extraClaim,
            UserDetails userDetails
    ){
        return  Jwts
                .builder()
                .setClaims(extraClaim)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSingInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateSecretKey(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[64]; // 512 bits key length
        secureRandom.nextBytes(key);

        StringBuilder keyHex = new StringBuilder();
        for (byte b : key) {
            keyHex.append(String.format("%02x", b));
        }
        return keyHex.toString();
    }
    public String generateRefreshToken(UserDetails user, String secretKey){
        long refreshTokenExpirationMs = 30 * 24 * 60 * 60 * 1000; // 30 days in milliseconds

        String refreshToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        System.out.println("Refresh token creation");
        return refreshToken;
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSingInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSingInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
