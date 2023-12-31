package com.decode.msapp.notification.service;

import com.decode.msapp.notification.config.RabbitMQMessageProducer;
import com.decode.msapp.notification.model.Message;
import com.decode.msapp.notification.model.Notification;
import com.decode.msapp.notification.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> getById(Integer id) {
        return notificationRepository.findById(id);
    }

    public void sendMessage(String message) {
        rabbitMQMessageProducer.publish(message, "exchange", "notification");
    }

    public void persistMessage(Message message) {
        Notification notification = Notification.builder()
                .timeSent(LocalDateTime.now())
                .messageBody(message)
                .userId(1)
                .build();
        this.save(notification);
    }

    public void deleteById(int id) {
        notificationRepository.deleteById(id);
    }

}
