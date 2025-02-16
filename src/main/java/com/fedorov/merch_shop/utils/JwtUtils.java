package com.fedorov.merch_shop.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtUtils {



    private final Algorithm algorithm;

    private final JWTVerifier verifier;

    public JwtUtils(@Value("${jwt.public.key}") String jwtPublicKey) {

        this.algorithm = Algorithm.HMAC256(jwtPublicKey);
        this.verifier = JWT.require(algorithm)
                .build();


    }


    public String createJWT(String username, String role, int id) {

        String jwtToken = JWT.create()
                .withIssuer("merch-shop")
                .withClaim("role", role)
                .withClaim("username", username)
                .withClaim("id", id)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000L))
                .withJWTId(UUID.randomUUID()
                        .toString())

                .sign(algorithm);


        return jwtToken;
    }

    public DecodedJWT verifyJWT(String jwtToken) {

        try {

            DecodedJWT decodedJWT = verifier.verify(jwtToken);

            return decodedJWT;

        } catch (JWTVerificationException e) {

            throw new RuntimeException(e);

        }


    }

    public String getToken(String header) {


        return header.substring(7);

    }

    public List<SimpleGrantedAuthority> getRole(DecodedJWT decodedJWT) {
        String role = decodedJWT.getClaim("role").asString();

        List<SimpleGrantedAuthority> authorities = role != null ?
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)) :
                Collections.emptyList();

        return authorities;
    }

    public String getUsername(DecodedJWT decodedJWT){

        return decodedJWT.getClaim("username").asString();

    }

    public int getId(DecodedJWT decodedJWT){

        return decodedJWT.getClaim("id").asInt();

    }





}
