package com.decode.msapp.notification.service;

import com.decode.msapp.notification.model.Message;
import com.decode.msapp.notification.model.Notification;
import com.decode.msapp.notification.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationService {

    NotificationRepository notificationRepository;

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> getById(Integer id) {
        return notificationRepository.findById(id);
    }

    public void sendMessage(Message message) {
    }

    public void ReceiveMessage(Message message) {
        Notification notification = Notification.builder()
                .timeSent(new Timestamp(System.currentTimeMillis()))
                .messageBody(message.getMessageBody())
                .userId(1)
                .build();
        save(notification);
    }

}
