package com.fedorov.merch_shop.repositories;

import com.fedorov.merch_shop.models.UserCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCustomRepo extends JpaRepository<UserCustom, Integer> {

    UserCustom findByUsername(String username);
    UserCustom findById(int id);

}
