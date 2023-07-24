package com.decode.msapp.notification.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final ConnectionFactory connectionFactory;

    @Bean
    public AmqpTemplate amqpTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jacksonConverter());
        return factory;
    }

    @Bean
    public MessageConverter jacksonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Creating queue with topic name "queue", durable - save message on broker restart
    @Bean
    Queue notificationQueue() {
        return new Queue("notification", false);
    }

    // Creating exchange named "exchange", to which we will bind our queues
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    // Binding above mentioned queues to our exchange named "exchange"
    // Messages with a key named "key" will be routed to queue "queue"
    @Bean
    Binding binding(Queue notificationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(notificationQueue).to(exchange).with("notification");
    }

//    @Bean
//    Queue queue2() {
//        return new Queue("queue2", false);
//    }


//    @Bean
//    Binding binding2(Queue queue2, TopicExchange exchange) {
//        return BindingBuilder.bind(queue2).to(exchange).with("k2");
//    }

    /*
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory();

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("10.211.55.19",5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
*/
    /*
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        //return new RabbitTemplate(connectionFactory());
        return new RabbitTemplate(connectionFactory);
    }
*/
}