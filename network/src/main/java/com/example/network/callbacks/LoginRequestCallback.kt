package com.example.network.callbacks

import androidx.lifecycle.MutableLiveData
import com.example.network.ErrorState
import com.example.network.RequestState
import com.example.network.SuccessState
import com.example.network.UseLoginRequestResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRequestCallback(private val liveData: MutableLiveData<RequestState>):
    Callback<UseLoginRequestResponse> {

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
}