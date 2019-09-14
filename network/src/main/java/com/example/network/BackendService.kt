package com.example.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendService {
    @POST("/login") fun login(@Body loginRequest: LoginRequest): Call<UseLoginRequestResponse>
}