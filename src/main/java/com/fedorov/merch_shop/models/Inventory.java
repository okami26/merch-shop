package com.fedorov.merch_shop.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "inventory")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_name")
    private String properties;

    @Column(name = "product_count")
    private int quantity;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    @JsonBackReference
    private UserCustom user;


}
