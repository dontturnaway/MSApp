package com.decode.msapp.notification.service;

import com.decode.msapp.notification.config.RabbitMQMessageProducer;
import com.decode.msapp.notification.model.Message;
import com.decode.msapp.notification.model.Notification;
import com.decode.msapp.notification.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
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

    @RabbitListener(queues = "notification")
    public void processMessage(String message) {
        Notification notification = Notification.builder()
                .timeSent(new Timestamp(System.currentTimeMillis()))
                .messageBody(message)
                .userId(1)
                .build();
        save(notification);
    }

    public void deleteById(int id) {
        notificationRepository.deleteById(id);
    }

}
