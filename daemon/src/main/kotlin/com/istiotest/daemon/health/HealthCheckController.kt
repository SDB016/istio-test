package com.istiotest.daemon.health

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping

@RestController
class HealthCheckController {
    @GetMapping("/health")
    fun healthCheck(): String {
        return "OK"
    }
}
