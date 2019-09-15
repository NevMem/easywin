package com.example.network

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Observable

interface NetworkProvider {
    fun login(login: String, password: String): Observable<RequestState<UserData>>

    fun makeSession(deviceId: String): Observable<RequestState<FastPaySession>>

    fun createInvoice(amount: Int, number: String, description: String): LiveData<RequestState<InvoiceResult>>

    fun createRoom(login: String, roomName: String): Observable<RequestState<RoomInfo>>

    fun loadRoomInfo(roomId: Int): Observable<RequestState<RoomInfo>>

    fun join(login: String, roomId: Int): Observable<RequestState<RoomInfo>>

    fun gotoPickMoney(roomId: Int)

    fun gotoLastStage(roomId: Int)
}