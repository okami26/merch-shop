package com.fedorov.merch_shop;


import com.fedorov.merch_shop.models.CoinHistory;
import com.fedorov.merch_shop.models.ErrorResponse;
import com.fedorov.merch_shop.models.UserCustom;
import com.fedorov.merch_shop.repositories.CoinHistoryRepo;
import com.fedorov.merch_shop.repositories.UserCustomRepo;
import com.fedorov.merch_shop.services.UserCustomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserCustomServiceTest {

    @InjectMocks
    private UserCustomService userCustomService;

    @Mock
    private UserCustomRepo userCustomRepo;

    @Mock
    private CoinHistoryRepo coinHistoryRepo;

    private UserCustom fromUser;
    private UserCustom toUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        fromUser = new UserCustom();
        fromUser.setUsername("user1");
        fromUser.setAccount_balance(1000);

        toUser = new UserCustom();
        toUser.setUsername("user2");
        toUser.setAccount_balance(500);


    }

    @Test
    public void testSendCoin_Success() {

        when(userCustomRepo.findByUsername("user1")).thenReturn(fromUser);
        when(userCustomRepo.findByUsername("user2")).thenReturn(toUser);


        ResponseEntity<?> response = userCustomService.sendCoin("user1", "user2", 200);


        assertEquals(200, response.getStatusCodeValue());
        assertEquals(800, fromUser.getAccount_balance());
        assertEquals(700, toUser.getAccount_balance());
        verify(coinHistoryRepo, times(1)).save(any(CoinHistory.class));
    }

    @Test
    public void testSendCoin_InsufficientFunds() {

        when(userCustomRepo.findByUsername("user1")).thenReturn(fromUser);
        when(userCustomRepo.findByUsername("user2")).thenReturn(toUser);


        ResponseEntity<?> response = userCustomService.sendCoin("user1", "user2", 1200);


        assertEquals(400, response.getStatusCodeValue());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("Недостаточно средств", errorResponse.getErrors());
        verify(coinHistoryRepo, never()).save(any(CoinHistory.class));
    }

    @Test
    public void testSendCoin_UserNotFound() {

        when(userCustomRepo.findByUsername("user1")).thenReturn(null);


        ResponseEntity<?> response = userCustomService.sendCoin("user1", "user2", 200);


        assertEquals(400, response.getStatusCodeValue());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("Пользователь не найден", errorResponse.getErrors());
    }
}

