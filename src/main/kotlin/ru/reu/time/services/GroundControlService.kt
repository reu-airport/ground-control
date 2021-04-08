package ru.reu.time.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import ru.reu.time.vo.Movement
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class GroundControlService(
    private val rabbitTemplate: RabbitTemplate
) {

    private val log = LoggerFactory.getLogger(javaClass)

    private val mapper = jacksonObjectMapper()

    var groundControl = ConcurrentHashMap<String, UUID>()

    @RabbitListener(queues = ["movementRequest"])
    fun movementRequest(message: String) {
        synchronized(this) {
            val receivedMessage = mapper.readValue(message, Movement::class.java)
            log.info("Received message: $receivedMessage")

            if (groundControl.containsKey("${receivedMessage.vertexFrom}${receivedMessage.vertexTo}")) {
                log.info("Found ground control with key: ${receivedMessage.vertexFrom}${receivedMessage.vertexTo}")
                rabbitTemplate.convertAndSend(
                    "movementPermission",
                    mapper.writeValueAsString(receivedMessage.apply {
                        isPermitted = false
                    })
                )
                log.info("Successful send to vehicleId: ${receivedMessage.vehicleId}")
            } else {
                groundControl["${receivedMessage.vertexFrom}${receivedMessage.vertexTo}"] = receivedMessage.vehicleId!!
                log.info("Put ground control with key: ${receivedMessage.vertexFrom}${receivedMessage.vertexTo} and value: ${receivedMessage.vehicleId}")
                rabbitTemplate.convertAndSend(
                    "movementPermission",
                    mapper.writeValueAsString(receivedMessage.apply {
                        isPermitted = true
                    })
                )
                log.info("Successful send to vehicleId: ${receivedMessage.vehicleId}")
            }
        }
    }

    @RabbitListener(queues = ["movementEnd"])
    fun groundControl(message: String) {
        synchronized(this) {
            val receivedMessage = mapper.readValue(message, Movement::class.java)
            log.info("Received message: $receivedMessage")
            groundControl.remove("${receivedMessage.vertexFrom}${receivedMessage.vertexTo}")
            log.info("Remove ground control with key: ${receivedMessage.vertexFrom}${receivedMessage.vertexTo}")
        }
    }

}
