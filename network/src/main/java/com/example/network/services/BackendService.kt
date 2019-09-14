package com.example.network.services

import com.example.network.LoginRequest
import com.example.network.UseLoginRequestResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendService {
    @POST("/login") fun login(@Body loginRequest: LoginRequest): Call<UseLoginRequestResponse>
}