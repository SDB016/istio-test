package com.istiotest.daemon.message

import org.springframework.stereotype.Service

@Service
class MessageService(
    private val messageRepository: MessageRepository
) {

    fun save(message: Message): Message = messageRepository.save(message)

}