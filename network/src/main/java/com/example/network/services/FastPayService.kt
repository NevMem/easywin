package com.example.network.services

import retrofit2.Call
import retrofit2.http.*

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
    val payer: String,
    val recipient: String,
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

data class BalanceBody(
    val address: String,
    val currencyCode: Int
)

data class BalanceResponce(
    val timestamp: String?,
    val message: String?,
    val data: BalanceResponseData
)

data class BalanceResponseData(
    val currencyCode: Int,
    val address: String,
    val total: Double
)

data class InvoiceInfoResponce(
    val timestamp: String?,
    val message: String?,
    val data: InvoiceInfoResponseData
)

data class InvoiceInfoResponseData(
    val number: String,
    val currencyCode: Int,
    val state: Int
)


interface FastPayService {
    @POST("api/v1/session")
    fun createSession(@Body sessionRequest: SessionRequest): Call<SessionResponse>

    @POST("api/v1/invoice")
    fun invoice(@Header("FPSID") sessionId: String, @Body invoiceBody: InvoiceBody): Call<InvoiceResponse>

    @POST("api/v1/transaction")
    fun getBalance(@Header("FPSID") sessionId: String, @Body balanceBody: BalanceBody): Call<BalanceResponce>

    @GET("api/v1/invoice/{currencyCode}/{number}/{recipient}")
    fun getInvoiceInfo(@Path("currencyCode") currencyCode: Int, @Path("number") number: String, @Path("recipient") recipient: String): Call<InvoiceInfoResponce>

}
