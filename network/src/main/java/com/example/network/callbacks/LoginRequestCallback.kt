package com.example.network.callbacks

import com.example.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRequestCallback(private val listener: RequestStateListener<RequestState<UserData>>):
    Callback<UseLoginRequestResponse> {

    override fun onFailure(call: Call<UseLoginRequestResponse>, t: Throwable) {
        listener.stateUpdated(ErrorState(t.message ?: "Unknown error"))
    }

    override fun onResponse(
        call: Call<UseLoginRequestResponse>,
        response: Response<UseLoginRequestResponse>
    ) {
        if (response.isSuccessful) {
            val result = response.body()
            if (result == null) {
                listener.stateUpdated(ErrorState("Some error occurred"))
            } else {
                val type = result.type
                if (type == null || (type != "ok" && type != "error")) {
                    listener.stateUpdated(ErrorState("Unknown server response"))
                    return
                }
                if (type == "ok") {
                    val userData = result.payload
                    if (userData != null) {
                        listener.stateUpdated(SuccessState(userData))
                    } else {
                        listener.stateUpdated(ErrorState("Unknown server response"))
                    }
                } else {
                    val error = result.error!!
                    listener.stateUpdated(ErrorState(error))
                }
            }
        } else {
            listener.stateUpdated(ErrorState("Some error occurred"))
        }
    }
}