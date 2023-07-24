package com.decode.msapp.notification.queue;

import com.decode.msapp.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class NotificationListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${app.rabbitmq.notification-queue}")
    public void processMessage(String message) {
        log.info("Consumed {} from queue", message);
        notificationService.persistMessage(message);
    }
}
