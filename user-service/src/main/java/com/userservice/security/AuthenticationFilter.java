package com.userservice.security;

import ch.qos.logback.core.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.dto.RequestLogin;
import com.userservice.dto.UserDto;
import com.userservice.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final UserService userService;
    private final Environment env;

    public AuthenticationFilter(
            AuthenticationManager authenticationManager,
            UserService userService,
            Environment env
    ) {
        super.setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        try {
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
            logger.info("password: {} " + creds.getPwd());
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    creds.getEmail(),
                    creds.getPwd(),
                    new ArrayList<>()
            );
            logger.info("authToeken: {} " + authToken.toString());
            return getAuthenticationManager().authenticate(
                    authToken
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        logger.info("success called");
        logger.info(((User) authResult.getPrincipal()).getUsername());
        String username = ((User) authResult.getPrincipal()).getUsername();
        UserDto userDto = userService.getUserByEmail(username);

        String token = Jwts
                .builder()
                .setSubject(userDto.getUserId())
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                +Long.parseLong(StringUtil.nullStringToEmpty(env.getProperty("token.expiration_time"))))
                )
                .signWith(SignatureAlgorithm.HS256,env.getProperty("token.secret"))
                .compact();
        response.addHeader("token",token);
        response.addHeader("userId",userDto.getUserId());

    }


}
