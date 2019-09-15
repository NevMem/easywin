package com.example.network.services

import com.example.network.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendService {
    @POST("/login") fun login(@Body loginRequest: LoginRequest): Call<UseLoginRequestResponse>

    @POST("/createRoom") fun createRoom(@Body createRoom: CreateRoomRequest): Call<RoomInfoResponse>

    @POST("/roomInfo") fun loadRoomInfo(@Body request: RoomIdRequest): Call<RoomInfoResponse>

    @POST("/join") fun join(@Body request: JoinRequest): Call<RoomInfoResponse>

    @POST("/gotoPickMoney") fun gotoPickMoney(@Body request: RoomIdRequest): Call<Void>

    @POST("/gotoLastStage") fun gotoLastStage(@Body request: RoomIdRequest): Call<Void>
}