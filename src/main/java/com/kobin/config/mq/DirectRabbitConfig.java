package com.kobin.config.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author shibinbin on 2017/11/8.
 */
@Configuration
public class DirectRabbitConfig {

    @Value("${rabbitmq.directqueue}")
    String queueName;

    @Value("${rabbitmq.directexchange}")
    String exchangeName;

    @Value("${rabbitmq.directroutingkey}")
    String routingKey;

    /**
     * queue默认是开启持久化的
     * @return
     */
    @Bean
    public Queue directQueue(){
        return new Queue(queueName);
    }

    /**
     * 默认是开启持久化的exchange
     * @return
     */
    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(exchangeName);
    }

    /**
     * 按照queue和exchange和routingKey做路由
     * @param directQueue
     * @param directExchange
     * @return
     */
    @Bean
    Binding bindingDirect(Queue directQueue, DirectExchange directExchange){
        return BindingBuilder.bind(directQueue).to(directExchange).with(routingKey);
    }
}
