package Dmitry.CelestialForge.Containers.RabbitMQ;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserMessageListener {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    @RabbitHandler
    public void receiveMessage(Message message) {
        String messageContent = new String(message.getBody());
        System.out.println("Received message: " + messageContent);
    }
}