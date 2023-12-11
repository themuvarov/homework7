package com.example.billingservice;

import com.example.billingservice.model.AccountDto;
import com.example.billingservice.model.BillingDto;
import com.example.billingservice.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/billing/v1")
@RequiredArgsConstructor
public class BillingEndpoint {

    private final BillingService billingService;

    private static final Logger LOG = LogManager.getLogger();

    @PostMapping(value = "/account",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<String> account(@RequestBody AccountDto dto) {
        LOG.info("Add account for agent {}", dto.getAgent());
        billingService.createAccount(dto);
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/add",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<BigDecimal> add(@RequestBody BillingDto dto) {
        LOG.info("Add money for agent {} {}", dto.getAgent(), dto.getSum());
        BigDecimal account = billingService.addMoney(dto);
        return ResponseEntity.ok(account);
    }

    @PostMapping(value = "/bill",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<BigDecimal> bill(@RequestBody BillingDto billingDto) {
        LOG.info("Billing for agent {} {}", billingDto.getAgent(), billingDto.getSum());
        BigDecimal account = billingService.billTransaction(billingDto);
        if (account.compareTo(BigDecimal.valueOf(-1)) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        return ResponseEntity.ok(account);
    }
    @GetMapping(value = "/current/{agent}")
    ResponseEntity<BigDecimal> current(@PathVariable String agent) {
        LOG.info("Account for agent {}", agent);
        BigDecimal account = billingService.get(agent);
        return ResponseEntity.ok(account);
    }


}
