package com.fedorov.merch_shop.repositories;

import com.fedorov.merch_shop.models.Inventory;
import com.fedorov.merch_shop.models.UserCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Integer> {

    Inventory findByUserAndAndProperties(UserCustom userCustom, String itemName);

}
