package com.example.billingservice.repository;

import com.example.billingservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAgent(String user);

    @Modifying
    @Query("update Account a set a.amount = a.amount + ?1 where a.agent = ?2")
    void addMoney(BigDecimal sum, String agent);

    @Modifying
    @Query("update Account a set a.amount = a.amount - ?1 where a.agent = ?2")
    void removeMoney(BigDecimal sum, String agent);

}