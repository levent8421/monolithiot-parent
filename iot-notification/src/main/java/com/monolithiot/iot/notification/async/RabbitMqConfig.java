package com.monolithiot.iot.notification.async;

import com.monolithiot.iot.commons.context.ApplicationConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Configuration
public class RabbitMqConfig {
    private final RabbitTemplate rabbitTemplate;
    private final MqConfirmCallback confirmCallback;
    private final MqReturnCallback returnCallback;

    public RabbitMqConfig(RabbitTemplate rabbitTemplate,
                          MqConfirmCallback confirmCallback,
                          MqReturnCallback returnCallback) {
        this.rabbitTemplate = rabbitTemplate;
        this.confirmCallback = confirmCallback;
        this.returnCallback = returnCallback;
    }

    @PostConstruct
    public void init() {
        initRabbitMqTemplate();
    }

    /**
     * Initialize the RabbitMqTemplate
     */
    private void initRabbitMqTemplate() {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
    }

    /**
     * emial queue
     *
     * @return 队列
     */
    @Bean(name = ApplicationConstants.MessageQueue.EMAIL_QUEUE_NAME)
    public Queue emailQueue() {
        return new Queue(ApplicationConstants.MessageQueue.EMAIL_QUEUE_NAME);
    }

    /**
     * Exchange exchange name
     *
     * @return Exchange
     */
    @Bean
    public Exchange emailExchange() {
        return new DirectExchange(ApplicationConstants.MessageQueue.EMAIL_EXCHANGE,
                true, false, null);
    }

    /**
     * 申明绑定
     *
     * @return Binding
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(emailQueue())
                .to(emailExchange())
                .with(ApplicationConstants.MessageQueue.EMAIL_ROUTING)
                .noargs();
    }
}
