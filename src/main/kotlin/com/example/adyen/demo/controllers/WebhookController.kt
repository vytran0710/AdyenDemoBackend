package com.example.adyen.demo.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Suppress("unused")
@RestController
@RequestMapping("/api/webhooks")
class WebhookController {

    @PostMapping("/notifications")
    fun handleNotification(@RequestBody payload: Map<String, Any>): ResponseEntity<String> {
        return ResponseEntity.ok("[accepted]")
    }
}