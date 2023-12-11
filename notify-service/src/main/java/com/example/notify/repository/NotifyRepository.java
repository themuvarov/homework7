package com.example.notify.repository;

import com.example.notify.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface NotifyRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByAgent(String user);

}