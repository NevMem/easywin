package com.example.network.callbacks

import androidx.lifecycle.MutableLiveData
import com.example.network.*
import com.example.network.services.SessionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SessionRequestCallback(private val listener: RequestStateListener<RequestState<FastPaySession>>) :
    Callback<SessionResponse> {

    override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
        listener.stateUpdated(ErrorState(t.message.toString()))
    }

    override fun onResponse(call: Call<SessionResponse>, response: Response<SessionResponse>) {
        if (response.isSuccessful) {
            val result = response.body()
            if (result == null) {
                listener.stateUpdated(ErrorState("Unknown server response"))
                return
            }
            listener.stateUpdated(SuccessState(FastPaySession(result.data!!)))
        } else {
            listener.stateUpdated(ErrorState(response.message()))
        }
    }

}
