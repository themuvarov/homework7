package com.example.billingservice.service;

import com.example.billingservice.model.Account;
import com.example.billingservice.model.AccountDto;
import com.example.billingservice.model.BillingDto;
import com.example.billingservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final AccountRepository repository;

    public void createAccount(AccountDto dto) {
        Account account =  repository.findByAgent(dto.getAgent());
        if (account == null) {
            account = new Account();
            account.setAgent(dto.getAgent());
            account.setAmount(BigDecimal.ZERO);
            repository.save(account);
        }
    }

    @Transactional
    public BigDecimal billTransaction(BillingDto dto) {
        Account account =  repository.findByAgent(dto.getAgent());
        if (account == null) {
            throw new RuntimeException("Account not found " + dto.getAgent());
        } else {
            if (account.getAmount().compareTo(dto.getSum()) < 0) {
                return BigDecimal.valueOf(-1);
            }
            repository.removeMoney(dto.getSum(), dto.getAgent());
        }

        return account.getAmount().subtract(dto.getSum());
    }

    @Transactional
    public BigDecimal addMoney(BillingDto dto) {
        Account account =  repository.findByAgent(dto.getAgent());
        if (account == null) {
            account = new Account();
            account.setAgent(dto.getAgent());
            account.setAmount(dto.getSum());
            repository.save(account);
        } else {
            repository.addMoney(dto.getSum(), dto.getAgent());
        }

        return account.getAmount();
    }

    public BigDecimal get(String dto) {
        Account account =  repository.findByAgent(dto);
        return account.getAmount();
    }

}
