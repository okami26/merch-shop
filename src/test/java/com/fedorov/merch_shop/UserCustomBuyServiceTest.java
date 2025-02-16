package com.fedorov.merch_shop;

import com.fedorov.merch_shop.models.*;
import com.fedorov.merch_shop.repositories.InventoryRepo;
import com.fedorov.merch_shop.repositories.MerchRepo;
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

public class UserCustomBuyServiceTest {

    @InjectMocks
    private UserCustomService userCustomService;

    @Mock
    private UserCustomRepo userCustomRepo;

    @Mock
    private MerchRepo merchRepo;

    @Mock
    private InventoryRepo inventoryRepo;

    private UserCustom userCustom;
    private Merch merch;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userCustom = new UserCustom();
        userCustom.setUsername("user1");
        userCustom.setAccount_balance(1000);

        merch = new Merch();
        merch.setName("T-Shirt");
        merch.setPrice(500);
    }

    @Test
    public void testBuyItem_Success() {

        when(merchRepo.findByName("T-Shirt")).thenReturn(merch);
        when(userCustomRepo.findByUsername("user1")).thenReturn(userCustom);
        when(inventoryRepo.findByUserAndAndProperties(userCustom, "T-Shirt")).thenReturn(null);


        ResponseEntity<?> response = userCustomService.buyItem("T-Shirt", "user1");


        assertEquals(200, response.getStatusCodeValue());
        assertEquals(500, userCustom.getAccount_balance());
        verify(inventoryRepo, times(1)).save(any(Inventory.class));
    }

    @Test
    public void testBuyItem_InsufficientFunds() {

        merch.setPrice(500);
        userCustom.setAccount_balance(400);

        when(merchRepo.findByName("T-Shirt")).thenReturn(merch);
        when(userCustomRepo.findByUsername("user1")).thenReturn(userCustom);


        ResponseEntity<?> response = userCustomService.buyItem("T-Shirt", "user1");


        assertEquals(400, response.getStatusCodeValue());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Недостаточно средств", errorResponse.getErrors());
    }

    @Test
    public void testBuyItem_MerchNotFound() {

        when(merchRepo.findByName("T-Shirt")).thenReturn(null);


        ResponseEntity<?> response = userCustomService.buyItem("T-Shirt", "user1");


        assertEquals(400, response.getStatusCodeValue());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("Товар не найден", errorResponse.getErrors());
    }
}
