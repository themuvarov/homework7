package com.example.tarifservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/outward/v1")
public class Endpoint {

    private static final Logger LOG = LogManager.getLogger();

    @GetMapping(value = "/outward/{agent}")
    ResponseEntity<Integer> createOutward(@PathVariable String agent) {
        LOG.info("Outward for agent {}", agent);

        if (agent.length() % 2 == 0) {
            throw new RuntimeException(); // service failure
        }
        return ResponseEntity.ok(23);
    }

    @GetMapping(value = "/rollback/{agent}")
    ResponseEntity<Integer> rollbackOutward(@PathVariable String agent) {
        LOG.info("rollback Outward for agent {}" + agent);

        return ResponseEntity.ok(23);
    }
}
