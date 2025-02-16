//package com.fedorov.merch_shop;
//
//
//import com.fedorov.merch_shop.models.AuthRequest;
//import com.fedorov.merch_shop.models.AuthResponse;
//import com.fedorov.merch_shop.models.UserCustom;
//import com.fedorov.merch_shop.repositories.UserCustomRepo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class UserCustomServiceIntegrationTest {
//
//    @LocalServerPort
//    private int port = 8080;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private UserCustomRepo userCustomRepo;
//
//    private String authToken;
//
//    @BeforeEach
//    public void setUp() {
//
//        UserCustom user = new UserCustom();
//        user.setUsername("testuser");
//        user.setPassword("password");
//        user.setAccount_balance(1000);
//        userCustomRepo.save(user);
//        System.out.println(port);
//        System.out.println("11111111111111111111111111111111111111111111111111");
//
//        AuthRequest authRequest = new AuthRequest("testuser", "password");
//
//        ResponseEntity<AuthResponse> authResponse = restTemplate.postForEntity(
//                "http://localhost:" + port + "/api/auth",
//                authRequest,
//                AuthResponse.class
//        );
//
//        System.out.println("Auth API Response: " + authResponse.getStatusCode());
//
//        if (authResponse.getBody() == null) {
//            throw new RuntimeException("Auth response body is null! Status: " + authResponse.getStatusCode());
//        }
//
//        authToken = "Bearer " + authResponse.getBody().getToken();
//
//    }
//
//    @Test
//    public void testBuyItemIntegration() {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", authToken);
//
//        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/buy/t-shirt",
//                HttpMethod.POST, new org.springframework.http.HttpEntity<>(headers), String.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//}
//
//
