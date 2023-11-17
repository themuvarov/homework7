package com.example.billingservice;

import com.example.billingservice.model.BillingDto;
import com.example.billingservice.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/billing/v1")
@RequiredArgsConstructor
public class BillingEndpoint {

    private final BillingService billingService;

    private static final Logger LOG = LogManager.getLogger();

    @PostMapping(value = "/billing",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Integer> bill(@RequestBody BillingDto billingDto) {
        LOG.info("Calculate billing for agent {} ", billingDto.getAgent());
        Integer account = billingService.billTransaction(billingDto);
        return ResponseEntity.ok(account);
    }

    @PostMapping(value = "/rollback",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Integer> billRollabck(@RequestBody BillingDto billingDto) {
        LOG.info("Rollback billing for agent {} ", billingDto.getAgent());
        Integer account = billingService.rollbackTransaction(billingDto);
        return ResponseEntity.ok(account);
    }


}
