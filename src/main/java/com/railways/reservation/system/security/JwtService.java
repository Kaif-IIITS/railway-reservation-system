package com.railways.reservation.system.security;

import com.railways.reservation.system.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import static com.railways.reservation.system.exception.ErrorCode.INVALID_TOKEN_TYPE;
import static com.railways.reservation.system.exception.ErrorCode.REFRESH_TOKEN_EXPIRED;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.access.expiration}")
    private Long accessTokenExpiration;
    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpiration;

    private SecretKey secretKey(String secretKey){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String username){
        Map<String, Object> claims = Map.of("token_type", "ACCESS_TOKEN");
        return buildToken(username,claims,accessTokenExpiration);
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = Map.of("token_type", "REFRESH_TOKEN");
        return buildToken(username,claims,refreshTokenExpiration);
    }

    public boolean isTokenValid(String token , String expectedUsername){
        String username = extractUsername(token);
        return username.equals(expectedUsername) && !isTokenExpired(token);
    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    public Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String buildToken(String username,Map<String, Object> claims,Long expiration){
         return
                 Jwts.builder()
                         .claims(claims)
                         .subject(username)
                         .issuedAt(new Date(System.currentTimeMillis()))
                         .expiration(new Date(System.currentTimeMillis()+expiration))
                         .signWith(secretKey(this.secretKey))
                         .compact();
    }

    public String refreshAccessToken(String refreshToken){
        Claims claims = extractClaims(refreshToken);
        if(!"REFRESH_TOKEN".equals(claims.get("token_type", String.class))){
            throw new BusinessException(INVALID_TOKEN_TYPE);
        }

        if(claims.getExpiration().before(new Date())){
            throw new BusinessException(REFRESH_TOKEN_EXPIRED);
        }

        return generateAccessToken(claims.getSubject());
    }

}
