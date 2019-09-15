package com.example.network

data class RoomInfo(
    val roomId: Int,
    val roomName: String,
    val state: String,
    val amountOfSpent: Double,
    val owner: UserData,
    val users: List<UserData>)