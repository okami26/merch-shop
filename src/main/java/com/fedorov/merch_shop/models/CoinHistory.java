package com.fedorov.merch_shop.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "coin_history")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoinHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "from_user")
    private String fromUser;


    @Column(name = "to_user")
    private String toUser;

    @Column(name = "coins")
    private int coins;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    @JsonBackReference
    private UserCustom user;

}
