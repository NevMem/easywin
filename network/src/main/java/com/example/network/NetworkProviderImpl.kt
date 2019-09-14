package com.example.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
            backendService.login(LoginRequest(login, password)).enqueue(object : Callback<UseLoginRequestResponse> {
                override fun onFailure(call: Call<UseLoginRequestResponse>, t: Throwable) {
                    liveData.postValue(ErrorState(t.message ?: "Unknown error"))
                }

                override fun onResponse(
                    call: Call<UseLoginRequestResponse>,
                    response: Response<UseLoginRequestResponse>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        if (result == null) {
                            liveData.postValue(ErrorState("Some error occurred"))
                        } else {
                            val type = result.type
                            if (type == null || (type != "ok" && type != "error")) {
                                liveData.postValue(ErrorState("Unknown server response"))
                                return
                            }
                            if (type == "ok") {
                                val userData = result.payload
                                if (userData != null) {
                                    liveData.postValue(SuccessState(userData))
                                } else {
                                    liveData.postValue(ErrorState("Unknown server response"))
                                }
                            } else {
                                val error = result.error!!
                                liveData.postValue(ErrorState(error))
                            }
                        }
                    } else {
                        liveData.postValue(ErrorState("Some error occurred"))
                    }
                }

            })
        }

        return liveData
    }
}