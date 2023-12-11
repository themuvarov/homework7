package com.example.notify.service;

import com.example.notify.model.Notification;
import com.example.notify.model.NotifyMessage;
import com.example.notify.repository.NotifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotifyService {

    private final NotifyRepository repository;

    @Transactional
    public List<String> getNotifications(String agent) {
        List<Notification> notificationList = repository.findByAgent(agent);
        return notificationList.stream().map(msg -> {
            return msg.getTime() + " - " + msg.getMessage();
        }).collect(Collectors.toList());
    }

    public Notification saveNotification(NotifyMessage message) {
        Notification notification = new Notification();
        notification.setAgent(message.getAgent());
        notification.setMessage(message.getMessage());
        notification.setTime(LocalDateTime.now());
        return repository.save(notification);
    }
}
