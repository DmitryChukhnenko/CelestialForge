package Dmitry.CelestialForge.Containers.RabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // Названия для очередей и обменников
    public static final String QUEUE_NAME = "user-queue";
    public static final String EXCHANGE_NAME = "user-exchange";
    public static final String ROUTING_KEY = "user.routing.key";

    // Определяем очередь
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);  // true — очередь будет долговечной
    }

    // Определяем обменник (можно использовать разные типы, но для простоты возьмем topic)
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // Связываем очередь с обменником с помощью routing key
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY);
    }
}

