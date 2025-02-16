package com.fedorov.merch_shop.utils;

import com.fedorov.merch_shop.models.CoinHistory;
import com.fedorov.merch_shop.models.CoinHistoryDTO;
import com.fedorov.merch_shop.models.Inventory;
import com.fedorov.merch_shop.models.InventoryDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ConverterDTO {


    public List<CoinHistoryDTO> convertTo(List<CoinHistory> coinHistoryList) {

        List<CoinHistoryDTO> coinHistoryDTOList = new ArrayList<>();


        coinHistoryList.forEach(coinHistory -> {

            CoinHistoryDTO coinHistoryDTO = new CoinHistoryDTO(coinHistory.getToUser(), coinHistory.getCoins());

            coinHistoryDTOList.add(coinHistoryDTO);

            System.out.println(coinHistory.getCoins());

        });

        return coinHistoryDTOList;

    }
    public List<CoinHistoryDTO> convertFrom(List<CoinHistory> coinHistoryList) {

        List<CoinHistoryDTO> coinHistoryDTOList = new ArrayList<>();


        coinHistoryList.forEach(coinHistory -> {

            CoinHistoryDTO coinHistoryDTO = new CoinHistoryDTO(coinHistory.getFromUser(), coinHistory.getCoins());

            coinHistoryDTOList.add(coinHistoryDTO);



        });

        return coinHistoryDTOList;

    }


    public List<InventoryDTO> convertInventory(List<Inventory> inventoryList) {

        List<InventoryDTO> inventoryDTOList = new ArrayList<>();


        inventoryList.forEach(inventory -> {

            InventoryDTO inventoryDTO = new InventoryDTO(inventory.getProperties(), inventory.getQuantity());

            inventoryDTOList.add(inventoryDTO);



        });

        return inventoryDTOList;

    }

}
