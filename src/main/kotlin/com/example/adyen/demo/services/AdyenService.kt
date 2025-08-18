package com.example.adyen.demo.services

import com.example.adyen.demo.configurations.AdyenConfig
import com.example.adyen.demo.configurations.AdyenConstants
import com.example.adyen.demo.models.PaymentRequest.BrowserInfo
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class AdyenService(private val config: AdyenConfig) {
    private val client = OkHttpClient()

    // To authenticate backend with Adyen
    private val apiKey: String
        get() {
            return config.apiKey
        }
    private val merchantAccount: String
        get() {
            return config.merchantAccount
        }
    // To authenticate frontend with Adyen
    private val clientKey: String
        get() {
            return config.clientKey
        }

    // Frontend fetching client key to authenticate
    fun getComponentClientKey(): String {
        return clientKey
    }

    // Get payment methods --> Can be intercepted to filter payment methods
    fun getPaymentMethods(amount: Int, currency: String, countryCode: String, shopperLocale: String): String {
        val requestBody = mapOf(
            "amount" to mapOf(
                "value" to amount,
                "currency" to currency
            ),
            "merchantAccount" to merchantAccount,
            "countryCode" to countryCode,
            "shopperLocale" to shopperLocale
        )

        val request = Request.Builder()
            .url("${AdyenConstants.ADYEN_ENDPOINT}/${AdyenConstants.ADYEN_VERSION}/paymentMethods")
            .addHeader("X-API-Key", apiKey)
            .addHeader("Content-Type", "application/json")
            .post(
                ObjectMapper()
                    .writeValueAsString(requestBody)
                    .toRequestBody("application/json".toMediaTypeOrNull())
            )
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            return response.body.string()
        }
    }

    // Submit payment
    fun initiatePayment(
        amount: Long,
        currency: String,
        reference: String,
        redirectUrl: String,
        channel: String,
        paymentMethod: Map<String, Any>,
        browserInfo: BrowserInfo
    ): String {
        val requestBody = mapOf(
            "amount" to mapOf(
                "currency" to currency,
                "value" to amount
            ),
            "merchantAccount" to merchantAccount,
            "reference" to reference,
            "returnUrl" to redirectUrl,
            "channel" to channel,
            "paymentMethod" to paymentMethod,
            "browserInfo" to mapOf(
                "userAgent" to browserInfo.userAgent,
                "acceptHeader" to browserInfo.acceptHeader
            ),
            // Dummy email
            "shopperEmail" to "email@test.com"
        )

        val request = Request.Builder()
            .url("${AdyenConstants.ADYEN_ENDPOINT}/${AdyenConstants.ADYEN_VERSION}/payments")
            .addHeader("X-API-Key", apiKey)
            .addHeader("Content-Type", "application/json")
            .post(
                ObjectMapper()
                    .writeValueAsString(requestBody)
                    .toRequestBody("application/json".toMediaTypeOrNull())
            )
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response, $request, $requestBody")
            return response.body.string()
        }
    }

    // Submit additional actions (challengeShopper, ...)
    fun submitPaymentDetails(requestBody: Map<String, Any>): String {
        val request = Request.Builder()
            .url("${AdyenConstants.ADYEN_ENDPOINT}/${AdyenConstants.ADYEN_VERSION}/payments/details")
            .addHeader("X-API-Key", apiKey)
            .addHeader("Content-Type", "application/json")
            .post(
                ObjectMapper()
                    .writeValueAsString(requestBody)
                    .toRequestBody("application/json".toMediaTypeOrNull())
            )
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            return response.body.string()
        }
    }
}