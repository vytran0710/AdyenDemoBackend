package com.example.adyen.demo.configurations

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "adyen")
class AdyenConfig {
    lateinit var apiKey: String
    lateinit var merchantAccount: String
    lateinit var clientKey: String
}