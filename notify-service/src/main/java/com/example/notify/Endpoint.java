package com.example.notify;

import com.example.notify.service.NotifyService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifier/v1")
@RequiredArgsConstructor
public class Endpoint {

    private final NotifyService notifyService;

    private static final Logger LOG = LogManager.getLogger();

    @GetMapping(value = "/{agent}")
    ResponseEntity<List<String>> current(@PathVariable String agent) {
        LOG.info("Account for agent {}", agent);
        List<String> result = notifyService.getNotifications(agent);
        return ResponseEntity.ok(result);
    }

}
