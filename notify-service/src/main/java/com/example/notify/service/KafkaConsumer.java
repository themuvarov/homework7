package com.example.notify.service;

import com.example.notify.model.NotifyMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final NotifyService notifyService;
    @KafkaListener(topics = "notify", groupId = "notifier", containerFactory = "kafkaListenerContainerFactory")
    public void listenGroupFoo(NotifyMessage message) {
        log.info("Notify service Received Message in group rent1: {} {}", message.getMessage(), message.getAgent());
        notifyService.saveNotification(message);
    }
}
