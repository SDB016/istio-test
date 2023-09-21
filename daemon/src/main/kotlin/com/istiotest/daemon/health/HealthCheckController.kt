package com.istiotest.daemon.health

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Mono

@RestController
class HealthCheckController {
    @GetMapping("/health")
    fun healthCheck(): Mono<String> {
        return Mono.just("OK")
    }
}
