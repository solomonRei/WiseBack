package com.teamback.wise.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.to-flask.queue.name}")
    private String toFlaskQueue;

    @Value("${rabbitmq.from-flask.queue.name}")
    private String fromFlaskQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.to-flask.routing.key}")
    private String toFlaskRoutingKey;

    @Value("${rabbitmq.from-flask.routing.key}")
    private String fromFlaskRoutingKey;

    @Bean
    public Queue toFlaskQueue() {
        return new Queue(toFlaskQueue, true);
    }

    @Bean
    public Queue fromFlaskQueue() {
        return new Queue(fromFlaskQueue, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding bindingToFlask() {
        return BindingBuilder.bind(toFlaskQueue())
                .to(exchange())
                .with(toFlaskRoutingKey);
    }

    @Bean
    public Binding bindingFromFlask() {
        return BindingBuilder.bind(fromFlaskQueue())
                .to(exchange())
                .with(fromFlaskRoutingKey);
    }
}
