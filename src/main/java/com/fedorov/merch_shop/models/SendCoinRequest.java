package com.fedorov.merch_shop.models;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendCoinRequest {


    @NotBlank(message = "Поле 'toUser' не может быть пустым")
    private String toUser;

    @Min(value = 1, message = "Количество монет должно быть больше 0")
    private int coins;


}
