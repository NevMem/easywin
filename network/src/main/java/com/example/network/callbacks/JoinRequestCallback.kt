package com.example.network.callbacks

import com.example.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinRequestCallback(private val listener: RequestStateListener<RequestState<RoomInfo>>)
    : Callback<RoomInfoResponse> {

    override fun onFailure(call: Call<RoomInfoResponse>, t: Throwable) {
        listener.stateUpdated(ErrorState("Connection error"))
    }

    override fun onResponse(
        call: Call<RoomInfoResponse>,
        response: Response<RoomInfoResponse>
    ) {
        if (!response.isSuccessful) {
            listener.stateUpdated(ErrorState("Error occurred"))
            return
        }
        val body = response.body()
        if (body == null || (body.type !in listOf("ok", "Error"))) {
            listener.stateUpdated(ErrorState("Body is null or type not ok/error"))
            return
        }
        if (body.type == "ok") {
            listener.stateUpdated(SuccessState(body.payload!!))
        } else {
            listener.stateUpdated(ErrorState(body.error.toString()))
        }
    }
}