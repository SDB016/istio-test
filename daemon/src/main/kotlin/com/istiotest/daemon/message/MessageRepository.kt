package com.istiotest.daemon.message

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface MessageRepository: ReactiveCrudRepository<Message, Long> {
//    @Query("SELECT * FROM message WHERE id = :id")
//    fun findById(id: Long): Flux<Message>
}