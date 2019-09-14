package com.example.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.network.callbacks.LoginRequestCallback
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkProviderImpl : NetworkProvider {
    companion object {
        const val BASE_URL = "http://192.168.43.66:8003"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val backendService = retrofit.create(BackendService::class.java)

    override fun login(login: String, password: String): LiveData<RequestState> {
        val liveData = MutableLiveData<RequestState>()
        liveData.postValue(PendingState)

        GlobalScope.launch {
            backendService
                .login(LoginRequest(login, password))
                .enqueue(LoginRequestCallback(liveData))
        }

        return liveData
    }
}