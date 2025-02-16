package com.fedorov.merch_shop.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;


@Entity
@Getter
@Table(name = "merch")
@Data
public class Merch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

}
