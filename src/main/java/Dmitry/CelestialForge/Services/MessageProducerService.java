package Dmitry.CelestialForge.Services;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Dmitry.CelestialForge.Containers.RabbitMQ.RabbitConfig;

@Service
public class MessageProducerService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String messageContent) {
        Message message = MessageBuilder.withBody(messageContent.getBytes())
                .setHeader("app", "spring-boot-rabbitmq")
                .build();
        rabbitTemplate.send(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, message);
    }
}

