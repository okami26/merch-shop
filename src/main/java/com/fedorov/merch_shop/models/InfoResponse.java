package com.fedorov.merch_shop.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoResponse {

    private int coins;

    private List<Inventory> inventory;

    private List<CoinHistoryDTO> received;

    private List<CoinHistoryDTO> sent;



}
