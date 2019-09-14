package com.example.network.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class SessionRequest(
    val deviceId: String,
    val deviceType: Int = 1
)

data class SessionResponse(
    val timestamp: String?,
    val message: String?,
    val data: String?
)

data class InvoiceBody(
    val amount: Int,
    val currencyCode: Int,
    val description: String,
    val number: String
)

data class InvoiceResponseData(
    val txId: String?,
    val result: String?
)

data class InvoiceResponse(
    val timestamp: String?,
    val message: String?,
    val data: InvoiceResponseData
)

interface FastPayService {
    @POST("api/v1/session")
    fun createSession(@Body sessionRequest: SessionRequest): Call<SessionResponse>

    @POST("api/v1/invoice")
    fun invoice(@Body invoiceBody: InvoiceBody): Call<InvoiceResponse>
}
