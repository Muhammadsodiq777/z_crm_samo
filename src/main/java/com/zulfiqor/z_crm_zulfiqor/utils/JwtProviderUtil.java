package com.zulfiqor.z_crm_zulfiqor.utils;

import com.zulfiqor.z_crm_zulfiqor.model.entity.Roles;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;
import com.zulfiqor.z_crm_zulfiqor.model.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtProviderUtil {

    private static String SECRET_KEY = "JJASDNJCNALDJHBCHCVQYEWCIUQWJDOICJNAKDSLNCLJKDC";
    public static Long ACCESS_EXPIRATION_TIME = 86000  * 1000L;
    public static Long REFRESH_EXPIRATION_TIME = 5 * 86000 * 1000L;

    public static String generateToken(User user, Date expirationDate) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getPhoneNumber());
        claims.put("roles", user.getRoles().stream().map(Roles::getRoleName).collect(Collectors.toList()));
        claims.put("fio", user.getFio() != null ? user.getFio() : "");

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.getPhoneNumber())
            .setExpiration(expirationDate)
            .setIssuedAt(new Date())
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public static String getSubject(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public static boolean validatedToken(String token) {
        Claims claims = getClaims(token);
        Date expirationDate = claims.getExpiration();
        return expirationDate.before(new Date());
    }

    public static Claims getClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private static Key getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public static String generateRefreshToken(Date expireRefreshDate) {
        return Jwts.builder()
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .setExpiration(expireRefreshDate)
            .compact();
    }
}
