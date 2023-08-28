package com.istiotest.daemon.sqs

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.istiotest.daemon.message.Message
import com.istiotest.daemon.message.MessageService
import org.springframework.stereotype.Service
import io.awspring.cloud.sqs.annotation.SqsListener
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.Payload

@Service
class SqsListener(
    private val objectMapper: ObjectMapper,
    private val messageService: MessageService
) {
    private val log = LoggerFactory.getLogger("SqsListener")

    @SqsListener(value = ["test-sqs.fifo"])
    fun listen(@Payload sqsMessage: String?, acknowledgement: Acknowledgement) {
        println("Received message: $sqsMessage")
        parseSQSMessage(sqsMessage)?.let { message: Message ->
            val savedMessageMono = messageService.save(message)
            savedMessageMono.subscribe{
                log.info("Id (${it.id}) message is saved")
                acknowledgement.acknowledge()
            }
        }

    }

    private fun parseSQSMessage(sqsMessage: String?): Message? =
        runCatching {
            objectMapper.readValue(sqsMessage, Message::class.java).also {
                log.info("Message received: $it")
            }
        }.onFailure { e -> handleException(e) }
        .getOrNull()


    private fun handleException(e: Throwable) {
        when (e) {
            is JsonProcessingException -> log.error("Error parsing message: $e")
            else -> log.error("Error processing message: $e")
        }
    }

}