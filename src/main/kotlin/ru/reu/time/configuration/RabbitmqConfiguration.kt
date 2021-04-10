package ru.reu.time.configuration

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitmqConfiguration {

    @Bean
    fun bindings(): Declarables {
        val queue = Queue("ground-control", true)
        val queue2 = Queue("movementRequest", true)
        val queue3 = Queue("movementEnd", true)
        val queue4 = Queue("movementPermission", true)
        val topicExchange = TopicExchange("")
        return Declarables(
            queue,
            queue2,
            queue3,
            queue4,
            BindingBuilder.bind(queue).to(topicExchange).with("ground-control"),
            BindingBuilder.bind(queue2).to(topicExchange).with("movementRequest"),
            BindingBuilder.bind(queue3).to(topicExchange).with("movementEnd"),
            BindingBuilder.bind(queue4).to(topicExchange).with("movementPermission")
        )
    }

}
