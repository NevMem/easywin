package com.example.network.callbacks

import com.example.network.ErrorState
import com.example.network.RequestState
import com.example.network.RequestStateListener
import com.example.network.SuccessState
import com.example.network.services.BalanceResponce
import com.example.network.services.InvoiceInfoResponce
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class getInvoiceInfoCallback(private val listener: RequestStateListener<RequestState<InvoiceInfoResponce>>) :
    Callback<InvoiceInfoResponce> {

    override fun onFailure(call: Call<InvoiceInfoResponce>, t: Throwable) {
        listener.stateUpdated(ErrorState(t.message.toString()))
    }


    override fun onResponse(call: Call<InvoiceInfoResponce>, response: Response<InvoiceInfoResponce>) {
        if (response.isSuccessful) {
            val result = response.body()
            if (result == null) {
                listener.stateUpdated(ErrorState("Unknown server response"))
                return
            }
            listener.stateUpdated(SuccessState(result))
        } else {
            listener.stateUpdated(ErrorState(response.message()))
        }
    }

}