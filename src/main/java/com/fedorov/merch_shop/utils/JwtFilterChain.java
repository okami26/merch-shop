package com.fedorov.merch_shop.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;



@Component
public class JwtFilterChain extends OncePerRequestFilter {


    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String requestURI = request.getRequestURI();


        if (requestURI.startsWith("/api/auth")) {

            filterChain.doFilter(request, response);
            return;

        }

        String authorizationHeader = request.getHeader("Authorization");


        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT token is missing or invalid");


        } else {

            try {
                String jwtToken = jwtUtils.getToken(authorizationHeader);

                DecodedJWT decodedJWT = jwtUtils.verifyJWT(jwtToken);

                String username = jwtUtils.getUsername(decodedJWT);


                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, jwtUtils.getRole(decodedJWT));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                filterChain.doFilter(request, response);

            } catch (RuntimeException e) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("JWT token is missing or invalid");

            }
        }
    }
}
