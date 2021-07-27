package by.logoped.logopedservice.jwt;

import by.logoped.logopedservice.exception.ActiveKeyNotValidException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class ActivateKeyJwtProvider {
    @Value("${jwt.claimSimpleKey}")
    private String claimSimpleKey;
    @Value("${jwt.claimIssuedAt}")
    private String claimIssuedAt;
    @Value("${jwt.claimExpiration}")
    private String claimExpiration;
    @Value("${jwt.secret}")
    private String secretKey;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String toJwt(String simpleKey){
        Claims claims = Jwts.claims().setSubject("activate key");
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiration = issuedAt.plusHours(24);
        claims.put(claimSimpleKey, simpleKey);
        claims.put(claimIssuedAt, issuedAt.toString());
        claims.put(claimExpiration, expiration.toString());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Map<String, Object> getClaimsMap(String jwtKey){
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtKey);
            return new HashMap<>(claimsJws.getBody());
        }catch (JwtException | IllegalArgumentException e){
            throw new ActiveKeyNotValidException("Activate key not valid");
        }
    }
}
