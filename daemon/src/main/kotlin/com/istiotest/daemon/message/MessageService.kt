package com.istiotest.daemon.message

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class MessageService(
    private val messageRepository: MessageRepository
) {

    fun save(message: Message): Mono<Message> = messageRepository.save(message)

}