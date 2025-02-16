package com.fedorov.merch_shop.services;


import com.fedorov.merch_shop.models.*;
import com.fedorov.merch_shop.repositories.CoinHistoryRepo;
import com.fedorov.merch_shop.repositories.InventoryRepo;
import com.fedorov.merch_shop.repositories.MerchRepo;
import com.fedorov.merch_shop.repositories.UserCustomRepo;
import com.fedorov.merch_shop.utils.ConverterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserCustomService {

    @Autowired
    private UserCustomRepo userCustomRepo;


    @Autowired
    private CoinHistoryRepo coinHistoryRepo;

    @Autowired
    private ConverterDTO converterDTO;

    @Autowired
    private MerchRepo merchRepo;

    @Autowired
    private InventoryRepo inventoryRepo;

    public UserCustom getUser(String username) {

        return userCustomRepo.findByUsername(username);

    }

    public ResponseEntity<?> getInfo(String username) {


        UserCustom userCustom = userCustomRepo.findByUsername(username);

        if (userCustom == null) {

            ErrorResponse errorResponse = new ErrorResponse("Пользователь не найден");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);


        }

        return new ResponseEntity<>(InfoResponse.builder()
                .coins(userCustom.getAccount_balance())
                .inventory(userCustom.getInventory())
                .sent(getFromHistory(username))
                .received(getToHistory(username))
                .build(), HttpStatus.OK);



    }

    public List<CoinHistoryDTO> getFromHistory(String username) {


        return converterDTO.convertTo(coinHistoryRepo.findByFromUser(username));


    }
    public List<CoinHistoryDTO> getToHistory(String username) {


        return converterDTO.convertFrom(coinHistoryRepo.findByToUser(username));


    }

    @Transactional
    public ResponseEntity<?> sendCoin(String username1, String username2, int countCoins) {

        UserCustom fromUser = userCustomRepo.findByUsername(username1);
        UserCustom toUser = userCustomRepo.findByUsername(username2);

        if (fromUser != null && toUser != null) {


            if (fromUser.getAccount_balance() < countCoins) {
                ErrorResponse errorResponse = new ErrorResponse("Недостаточно средств");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

            }

            fromUser.setAccount_balance(fromUser.getAccount_balance() - countCoins);
            toUser.setAccount_balance(toUser.getAccount_balance() + countCoins);

            userCustomRepo.save(fromUser);
            userCustomRepo.save(toUser);

            coinHistoryRepo.save(CoinHistory.builder()
                    .fromUser(username1)
                    .toUser(username2)
                    .user(fromUser)
                    .coins(countCoins)
                    .build());

            return new ResponseEntity<>(HttpStatus.OK);


        }
        ErrorResponse errorResponse = new ErrorResponse("Пользователь не найден");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);


    }


    @Transactional
    public ResponseEntity<?> buyItem(String itemName, String username) {


        Merch merch = merchRepo.findByName(itemName);

        UserCustom userCustom = userCustomRepo.findByUsername(username);

        Inventory inventory = inventoryRepo.findByUserAndAndProperties(userCustom, itemName);

        if (merch == null) {

            ErrorResponse errorResponse = new ErrorResponse("Товар не найден");


            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);


        }

        if (userCustom.getAccount_balance() < merch.getPrice()) {

            ErrorResponse errorResponse = new ErrorResponse("Недостаточно средств");


            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        }

        if (inventory == null) {

            Inventory inventoryNew = Inventory.builder()
                    .user(userCustom)
                    .properties(itemName)
                    .quantity(1)
                    .build();

            inventoryRepo.save(inventoryNew);

            userCustom.setAccount_balance(userCustom.getAccount_balance() - merch.getPrice());
        }

        else {

            inventory.setQuantity(inventory.getQuantity() + 1);
            userCustom.setAccount_balance(userCustom.getAccount_balance() - merch.getPrice());

        }

        return new ResponseEntity<>(HttpStatus.OK);

    }


}
