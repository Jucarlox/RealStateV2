package com.salesianostriana.dam.realstatev2.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log
@Service
public class JwtProvider {

    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.secret:asddñjmdkofdsñmdpdlsfklñsmdflkñmsdf}")
    private String jwtSecret;

    @Value("${jwt.duration:86400}")
    private int jwtLifeInSecond;

    private JwtParser parser;
}
