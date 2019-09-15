package com.example.easywin

import androidx.lifecycle.LiveData
import com.example.network.RequestState
import com.example.network.RoomInfo

enum class RoomHolderState {
    MANAGE,
    JOINED
}

interface RoomHolder {
    fun createRoom(roomName: String): LiveData<RequestState<RoomInfo>>

    fun join(roomId: Int): LiveData<RequestState<RoomInfo>>

    fun currentRoom(): LiveData<RoomInfo>

    fun state(): RoomHolderState

    fun gotoPickMoney()

    fun gotoLastStage()
}