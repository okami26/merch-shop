package com.fedorov.merch_shop.repositories;

import com.fedorov.merch_shop.models.Merch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchRepo extends JpaRepository<Merch, Integer> {


    Merch findByName(String name);

}
