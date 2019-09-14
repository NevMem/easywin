package com.example.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.network.callbacks.CreateInvoiceCallback
import com.example.network.callbacks.LoginRequestCallback
import com.example.network.callbacks.SessionRequestCallback
import com.example.network.services.BackendService
import com.example.network.services.FastPayService
import com.example.network.services.InvoiceBody
import com.example.network.services.SessionRequest
import io.reactivex.rxjava3.core.Observable
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
        amount: Int,
        number: String,
        description: String
    ): LiveData<RequestState<InvoiceResult>> {
        val liveData = MutableLiveData<RequestState<InvoiceResult>>()
        liveData.postValue(PendingState())

        fastPayService
            .invoice(InvoiceBody(amount, 810, description, number))
            .enqueue(CreateInvoiceCallback(number, liveData))

        return liveData
    }
}