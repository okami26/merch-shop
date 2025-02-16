package com.fedorov.merch_shop.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fedorov.merch_shop.models.*;

import com.fedorov.merch_shop.services.UserCustomDetailsService;
import com.fedorov.merch_shop.services.UserCustomService;
import com.fedorov.merch_shop.utils.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ApiController {


    @Autowired
    private JwtUtils jwtUtils;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserCustomDetailsService userCustomDetailsService;

    @Autowired
    private UserCustomService userCustomService;

    @Secured("ROLE_USER")
    @GetMapping("/info")
    public ResponseEntity<?> getInfo(@RequestHeader("Authorization") String bearerToken) {

        String jwtToken = jwtUtils.getToken(bearerToken);

        DecodedJWT decodedJWT = jwtUtils.verifyJWT(jwtToken);


        String username = jwtUtils.getUsername(decodedJWT);




        return userCustomService.getInfo(username);

    }

    @PostMapping("/auth")
    public ResponseEntity<?> authorization(@Valid @RequestBody AuthRequest authRequest) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()
        );

        try {
            UserCustom userCustom = userCustomService.getUser(authRequest.getUsername());
            if (userCustom != null) {

                Authentication authentication = authenticationManager.authenticate(token);

                authentication.isAuthenticated();

                String jwt = jwtUtils.createJWT(userCustom.getUsername(), userCustom.getRole(), userCustom.getId());

                AuthResponse authResponse = new AuthResponse(jwt);
                return new ResponseEntity<>(authResponse, HttpStatus.OK);
            }

            else {

                userCustomDetailsService.createUser(authRequest.getUsername(), authRequest.getPassword(), "USER");
                UserCustom userCustom2 = userCustomService.getUser(authRequest.getUsername());
                String jwt = jwtUtils.createJWT(userCustom2.getUsername(), userCustom2.getRole(), userCustom2.getId());
                AuthResponse authResponse = new AuthResponse(jwt);
                return new ResponseEntity<>(authResponse, HttpStatus.OK);
            }
        }

        catch (BadCredentialsException e) {

            ErrorResponse errorResponse = new ErrorResponse("Неверный пароль");

            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);

        }


    }

    @Secured("ROLE_USER")
    @PostMapping("/sendCoin")
    public ResponseEntity<?> sendCoin(@Valid @RequestBody SendCoinRequest request, @RequestHeader("Authorization") String bearerToken){


        String jwtToken = jwtUtils.getToken(bearerToken);

        DecodedJWT decodedJWT = jwtUtils.verifyJWT(jwtToken);


        String username = jwtUtils.getUsername(decodedJWT);

        return userCustomService.sendCoin(username, request.getToUser(), request.getCoins());
    }

    @Secured("ROLE_USER")
    @PostMapping("/buy/{item}")
    public ResponseEntity<?> buyItem(@PathVariable("item") String item, @RequestHeader("Authorization") String bearerToken) {

        String jwtToken = jwtUtils.getToken(bearerToken);

        DecodedJWT decodedJWT = jwtUtils.verifyJWT(jwtToken);


        String username = jwtUtils.getUsername(decodedJWT);

        return userCustomService.buyItem(item, username);



    }




}
