package com.example.network.callbacks

import androidx.lifecycle.MutableLiveData
import com.example.network.*
import com.example.network.services.BalanceResponce
import com.example.network.services.InvoiceResponse
import com.example.network.services.SessionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class getBalanceCallback(private val listener: RequestStateListener<RequestState<Double>>) :
    Callback<BalanceResponce> {

    override fun onFailure(call: Call<BalanceResponce>, t: Throwable) {
        listener.stateUpdated(ErrorState(t.message.toString()))
    }



    override fun onResponse(call: Call<BalanceResponce>, response: Response<BalanceResponce>) {
        if (response.isSuccessful) {
            val result = response.body()
            if (result == null) {
                listener.stateUpdated(ErrorState("Unknown server response"))
                return
            }
            listener.stateUpdated(SuccessState(result.data!!.total))
        } else {
            listener.stateUpdated(ErrorState(response.message()))
        }
    }

}
