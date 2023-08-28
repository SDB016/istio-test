package com.istiotest.daemon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@EnableWebFlux
@SpringBootApplication
class DaemonApplication

fun main(args: Array<String>) {
    runApplication<DaemonApplication>(*args)
}
