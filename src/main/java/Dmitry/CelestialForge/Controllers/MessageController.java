package Dmitry.CelestialForge.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Dmitry.CelestialForge.Services.MessageProducerService;

@Controller
public class MessageController {

    private final MessageProducerService messageProducer;

    @Autowired
    public MessageController(MessageProducerService messageProducer) {
        this.messageProducer = messageProducer;
    }

    // Простое API для отправки сообщений в RabbitMQ
    @RequestMapping("/send")
    public String sendMessage(String message) {
        messageProducer.sendMessage(message);
        return "Message sent: " + message;
    }
}

