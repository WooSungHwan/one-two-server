package com.blackdog.onetwo.configuration.security;

import com.blackdog.onetwo.configuration.security.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;

    public String generateJwtToken(int seq) {
        Date now = new Date();
        String jwtToken = Jwts.builder()
                .setSubject("ONE-TWO-SERVER API")
                .setIssuer("ONE-TWO-USER")
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .setExpiration(new Date(now.getTime() + jwtProperties.getExpiration()))
                .claim("seq", seq)
                .compact();

        return jwtToken;
    }

    public String getJwtToken(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(authorization) || !authorization.startsWith("Bearer ")) {
            return null;
        }
        return authorization.substring(7);
    }

    public Long getSeq(String jwtToken) {
        return getClaims(jwtToken).get("seq", Long.class);
    }

    private Claims getClaims(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJwt(jwtToken)
                .getBody();
    }
}
