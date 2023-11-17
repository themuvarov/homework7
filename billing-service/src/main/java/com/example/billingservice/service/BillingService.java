package com.example.billingservice.service;

import com.example.billingservice.model.BillingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BillingService {

    Map<String, Integer> accounts = new HashMap<>();
    public Integer billTransaction(BillingDto dto) {
        Integer currentBalance = accounts.computeIfAbsent(dto.getAgent(), key -> 0) + dto.getSum();
        accounts.put(dto.getAgent(), currentBalance);
        return currentBalance;
    }

    public Integer rollbackTransaction(BillingDto dto) {
        Integer currentBalance = accounts.computeIfAbsent(dto.getAgent(), key -> 0) - dto.getSum();
        accounts.put(dto.getAgent(), currentBalance);
        return currentBalance;
    }

}
