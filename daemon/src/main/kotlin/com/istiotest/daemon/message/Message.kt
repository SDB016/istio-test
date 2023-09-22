package com.istiotest.daemon.message

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table


@Table(name="messages")
@Entity
class Message (
    val message: String,
    @Id @GeneratedValue var id: Long? = null
) {

}