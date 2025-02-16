//package com.fedorov.merch_shop;
//
//
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fedorov.merch_shop.controllers.ApiController;
//import com.fedorov.merch_shop.models.*;
//import com.fedorov.merch_shop.services.UserCustomDetailsService;
//import com.fedorov.merch_shop.services.UserCustomService;
//import com.fedorov.merch_shop.utils.JwtUtils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ApiControllerTests {
//
//    @Mock
//    private JwtUtils jwtUtils;
//
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @Mock
//    private UserCustomDetailsService userCustomDetailsService;
//
//    @Mock
//    private UserCustomService userCustomService;
//
//    @InjectMocks
//    private ApiController apiController;
//
//
//    private MockMvc mockMvc;
//
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setup() {
//        mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    void getInfo() throws Exception {
//
//
//        mockMvc.perform(get("/api/info").header("Authorization", "Bearer test"))
//                .andExpect(status().isOk());
//
//
//    }
//
//    @Test
//    void auth() throws Exception {
//
//        AuthRequest authRequest = new AuthRequest("username", "password");
//        String authJson = objectMapper.writeValueAsString(authRequest);
//
//        UserCustom userCustom = new UserCustom();
//        userCustom.setUsername("username");
//        userCustom.setPassword("password");
//        userCustom.setRole("USER");
//        when(userCustomService.getUser("username")).thenReturn(userCustom);
//
//
//        Authentication authentication = mock(Authentication.class);
//        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
//                .thenReturn(authentication);
//        when(authentication.isAuthenticated()).thenReturn(true);
//
//
//        String mockedJwt = "mock-jwt-token";
//        when(jwtUtils.createJWT("username", "USER", userCustom.getId())).thenReturn(mockedJwt);
//
//
//        mockMvc.perform(post("/api/auth")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(authJson))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").value(mockedJwt));
//    }
//
//
//    @Test
//    void sendCoin_ShouldReturnOk_WhenCoinSentSuccessfully() throws Exception {
//        // Arrange
//        String fromUser = "user1";
//        String toUser = "user2";
//        int coinsToTransfer = 10;
//        String bearerToken = "Bearer testToken";
//
//
//        when(jwtUtils.getToken(bearerToken)).thenReturn("testToken");
//        DecodedJWT decodedJWT = mock(DecodedJWT.class);
//        when(jwtUtils.verifyJWT("testToken")).thenReturn(decodedJWT);
//        when(jwtUtils.getUsername(decodedJWT)).thenReturn(fromUser);
//
//
//        when(userCustomService.sendCoin(fromUser, toUser, coinsToTransfer))
//                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
//
//
//        SendCoinRequest sendCoinRequest = new SendCoinRequest(toUser, coinsToTransfer);
//
//
//        mockMvc.perform(post("/api/sendCoin")
//                        .header("Authorization", bearerToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(sendCoinRequest)))
//                .andExpect(status().isOk());
//
//        verify(userCustomService, times(1)).sendCoin(fromUser, toUser, coinsToTransfer);
//    }
//
//    @Test
//    void sendCoin_ShouldReturnBadRequest_WhenUserNotFound() throws Exception {
//
//        String fromUser = "user1";
//        String toUser = "user2";
//        int coinsToTransfer = 10;
//        String bearerToken = "Bearer testToken";
//
//
//        when(jwtUtils.getToken(bearerToken)).thenReturn("testToken");
//        DecodedJWT decodedJWT = mock(DecodedJWT.class);
//        when(jwtUtils.verifyJWT("testToken")).thenReturn(decodedJWT);
//        when(jwtUtils.getUsername(decodedJWT)).thenReturn(fromUser);
//
//
//        when(userCustomService.sendCoin(fromUser, toUser, coinsToTransfer))
//                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
//
//        SendCoinRequest sendCoinRequest = new SendCoinRequest(toUser, coinsToTransfer);
//
//
//        mockMvc.perform(post("/api/sendCoin")
//                        .header("Authorization", bearerToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(sendCoinRequest)))
//                .andExpect(status().isBadRequest());
//
//    }
//    @Test
//    void sendCoin_ShouldReturnBadRequest_WhenNotEnoughCoins() throws Exception {
//        // Arrange
//        String fromUser = "user1";
//        String toUser = "user2";
//        int coinsToTransfer = 10;
//        String bearerToken = "Bearer testToken";
//
//
//        when(jwtUtils.getToken(bearerToken)).thenReturn("testToken");
//        DecodedJWT decodedJWT = mock(DecodedJWT.class);
//        when(jwtUtils.verifyJWT("testToken")).thenReturn(decodedJWT);
//        when(jwtUtils.getUsername(decodedJWT)).thenReturn(fromUser);
//
//
//        UserCustom fromUserCustom = new UserCustom();
//        fromUserCustom.setAccount_balance(5);
//        when(userCustomService.sendCoin(fromUser, toUser, coinsToTransfer))
//                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
//
//        SendCoinRequest sendCoinRequest = new SendCoinRequest(toUser, coinsToTransfer);
//
//
//        mockMvc.perform(post("/api/sendCoin")
//                        .header("Authorization", bearerToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(sendCoinRequest)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.errors").value("Недостаточно средств"));
//    }
//
//}
