package com.istiotest.daemon.message

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("messages")
class Message (
    val message: String,
    @Id var id: Long? = null
) {

}