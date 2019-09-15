package com.example.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.network.callbacks.*
import com.example.network.services.*
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkProviderImpl : NetworkProvider {
    companion object {
        const val BACKEND_BASE_URL = "http://192.168.43.66:8003"
        const val FAST_PLAY_BASE_URl = "http://89.208.84.235:31080"
    }

    private val backendRetrofit = Retrofit.Builder()
        .baseUrl(BACKEND_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val fastPayRetrofit = Retrofit.Builder()
        .baseUrl(FAST_PLAY_BASE_URl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val backendService = backendRetrofit.create(BackendService::class.java)
    private val fastPayService = fastPayRetrofit.create(FastPayService::class.java)

    override fun login(login: String, password: String): Observable<RequestState<UserData>> {
        return Observable.create {
            it.onNext(PendingState())
            backendService
                .login(LoginRequest(login, password))
                .enqueue(LoginRequestCallback(object : RequestStateListener<RequestState<UserData>> {
                    override fun stateUpdated(state: RequestState<UserData>) {
                        it.onNext(state)
                    }
                }))
        }
    }

    override fun makeSession(deviceId: String): Observable<RequestState<FastPaySession>> {
        return Observable.create {
            fastPayService
                .createSession(SessionRequest(deviceId))
                .enqueue(SessionRequestCallback(object : RequestStateListener<RequestState<FastPaySession>> {
                    override fun stateUpdated(state: RequestState<FastPaySession>) {
                        it.onNext(state)
                    }
                }))
        }
    }

    override fun createInvoice(
        sessionId: String,
        payer: String,
        recipient: String,
        amount: Int,
        number: String,
        description: String
    ): LiveData<RequestState<InvoiceResult>> {
        val liveData = MutableLiveData<RequestState<InvoiceResult>>()
        liveData.postValue(PendingState())

        fastPayService
            .invoice(sessionId, InvoiceBody(payer, recipient, amount, 810, description, number))
            .enqueue(CreateInvoiceCallback(number, liveData))

        return liveData
    }

    override fun createRoom(login: String, roomName: String): Observable<RequestState<RoomInfo>> {
        return Observable.create {
            backendService
                .createRoom(CreateRoomRequest(login, roomName))
                .enqueue(CreateRoomRequestCallback(object : RequestStateListener<RequestState<RoomInfo>> {
                    override fun stateUpdated(state: RequestState<RoomInfo>) {
                        it.onNext(state)
                        if (state is SuccessState) {
                            it.onComplete()
                        }
                    }
                }))
        }
    }

    override fun loadRoomInfo(roomId: Int): Observable<RequestState<RoomInfo>> {
        return Observable.create {
            backendService
                .loadRoomInfo(RoomIdRequest(roomId))
                .enqueue(LoadRoomInfoRequestCallback(object : RequestStateListener<RequestState<RoomInfo>> {
                    override fun stateUpdated(state: RequestState<RoomInfo>) {
                        it.onNext(state)
                        if (state is SuccessState) {
                            it.onComplete()
                        }
                    }
                }))
        }
    }

    override fun join(login: String, roomId: Int): Observable<RequestState<RoomInfo>> {
        return Observable.create {
            backendService
                .join(JoinRequest(login, roomId))
                .enqueue(JoinRequestCallback(object : RequestStateListener<RequestState<RoomInfo>> {
                    override fun stateUpdated(state: RequestState<RoomInfo>) {
                        it.onNext(state)
                        if (state is SuccessState) {
                            it.onComplete()
                        }
                    }
                }))
        }
    }

    override fun gotoPickMoney(roomId: Int) {
        GlobalScope.launch {
            backendService
                .gotoPickMoney(RoomIdRequest(roomId))
                .execute()
        }
    }

    override fun gotoLastStage(roomId: Int) {
        GlobalScope.launch {
            backendService
                .gotoLastStage(RoomIdRequest(roomId))
                .execute()
        }
    }

    override fun getUserBalance(
        sessionId: String,
        address: String,
        currencyCode: Int
    ): Observable<RequestState<Double>> {
        return Observable.create {
            fastPayService
                .getBalance(sessionId, BalanceBody(address, currencyCode))
                .enqueue(getBalanceCallback(object : RequestStateListener<RequestState<Double>> {
                    override fun stateUpdated(state: RequestState<Double>) {
                        it.onNext(state)
                    }
                }))
          
    override fun updateServerInfo(roomId: Int, roomInfo: RoomInfo) {
        GlobalScope.launch {
            backendService
                .updateServer(UpdateRoomInfoRequest(roomId, roomInfo))
                .execute()
        }
    }
}