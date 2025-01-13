package com.toshiba.intern.usercrud.utils;

import com.toshiba.intern.usercrud.service.security.UserDetailsImpl;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

//This class has 3 funtions:
//
// - generate a JWT from username, date, expiration, secret
// - get username from JWT
// - validate a JWT

@Component
public class JwtUtils
{
    Dotenv dotenv = Dotenv.configure().load();

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private final String JWT_SECRET = dotenv.get("JWT_SECRET"); // Thay thế bằng secret key của bạn
    private final long JWT_EXPIRATION = 604800000L; // 7 ngày

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + JWT_EXPIRATION))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
