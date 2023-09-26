package com.example.demo.config.jwt;
import com.example.demo.model.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.*;
import java.util.Date;

@Service
@Component
public class JwtService {
    private final String SECRET_KEY = "AUYUHIOJKNKMNDJSAHDASKLNDKANSDKBASUYFRTFWDEWOGJLDKSNFKSNC";
    private static final int EXPIRED_TIME_SHORT = 3600 * 1000;
    private static final int EXPIRED_TIME_LONG = 3600 * 24 * 1000;

    public String genToken(UserEntity userEntity, EXPIRED_TYPE type){
        int EXPIRED_TIME = (type == EXPIRED_TYPE.SHORT) ? EXPIRED_TIME_SHORT : EXPIRED_TIME_LONG;
        return Jwts.builder()
                .setSubject(userEntity.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRED_TIME))
                .signWith(getSignInKey())
                .compact();
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
    public String generatorAccessToken(UserEntity userEntity){return genToken(userEntity, EXPIRED_TYPE.SHORT);}
    public String generatorRefreshToken(UserEntity userEntity){
        return genToken(userEntity, EXPIRED_TYPE.LONG);
    }
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
            return true;
        }catch (Exception e){
            System.out.printf("JWT Error -> Message: %s ", e.getMessage());
        }
        return false;
    }

    public Date getExpired(String token) {
        return parseClaims(token).getExpiration();
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
