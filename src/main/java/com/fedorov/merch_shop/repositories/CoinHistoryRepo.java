package com.fedorov.merch_shop.repositories;

import com.fedorov.merch_shop.models.CoinHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoinHistoryRepo extends JpaRepository<CoinHistory, Integer> {

    List<CoinHistory> findByFromUser(String username);

    List<CoinHistory> findByToUser(String username) ;

}
