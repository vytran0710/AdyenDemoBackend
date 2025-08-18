package com.example.adyen.demo.controllers

import com.example.adyen.demo.models.PaymentRequest
import com.example.adyen.demo.services.AdyenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Suppress("unused")
@RestController
@RequestMapping("/api/payments")
class PaymentController(
    private val adyenService: AdyenService
) {
    @GetMapping("/getPaymentMethods")
    fun getPaymentMethods(
        @RequestParam amount: Int,
        @RequestParam currency: String,
        @RequestParam countryCode: String,
        @RequestParam shopperLocale: String
    ): ResponseEntity<String> {
        val response = adyenService.getPaymentMethods(
            amount = amount,
            currency = currency,
            countryCode = countryCode,
            shopperLocale = shopperLocale
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/getClientKey")
    fun getClientKey(): ResponseEntity<String> {
        val response = adyenService.getComponentClientKey()
        return ResponseEntity.ok(response)
    }

    @PostMapping("/initiate")
    fun initiatePayment(@RequestBody request: PaymentRequest): ResponseEntity<String> {
        val response = adyenService.initiatePayment(
            amount = request.amount,
            currency = request.currency,
            reference = request.reference,
            redirectUrl = request.redirectUrl,
            channel = request.channel,
            paymentMethod = request.paymentMethod,
            browserInfo = request.browserInfo
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/processDetails")
    fun processDetails(@RequestBody body: Map<String, Any>): ResponseEntity<String> {
        val response = adyenService.submitPaymentDetails(requestBody = body)
        return ResponseEntity.ok(response)
    }
}