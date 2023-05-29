package com.example.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    @Value("${jwt.token.issuer}")
    public String ISSUER;

    @Value("${jwt.token.signing.key}")
    public String SIGNING_KEY;

    public String getUsernameFromToken(String token){
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getSubject();
    }

    public String getRoleFromToken(String token){
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getClaim("roles").asArray(String.class)[0];
    }

    public Date getExpirationDateFromToken(String token){
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getExpiresAt();
    }

    private DecodedJWT getDecodedJWT(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SIGNING_KEY.getBytes())).build();
        return verifier.verify(token);
    }

    public String generateAccessToken(User user, Collection<? extends GrantedAuthority> authorities,
                                      int expirationTime) throws JsonProcessingException, IllegalArgumentException, JWTCreationException {
        return generateToken(user, authorities, expirationTime);
    }

    public String generateRefreshToken(User user, int expirationTime) throws JsonProcessingException, IllegalArgumentException, JWTCreationException {
        return generateToken(user, null, expirationTime);
    }

    private String generateToken(User user, Collection<? extends GrantedAuthority> authorities, int expirationTime) throws JsonProcessingException, IllegalArgumentException, JWTCreationException {
        JWTCreator.Builder tokenBuilder = JWT.create()
                .withSubject(user.getLogin())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .withIssuer(ISSUER);
        if (authorities != null)
            return tokenBuilder
                    .withClaim("roles",
                            authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(Algorithm.HMAC256(SIGNING_KEY.getBytes()));
        else
            return tokenBuilder.sign(Algorithm.HMAC256(SIGNING_KEY.getBytes()));
    }

}