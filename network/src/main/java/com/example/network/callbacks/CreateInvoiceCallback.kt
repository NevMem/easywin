package com.example.network.callbacks

import androidx.lifecycle.MutableLiveData
import com.example.network.ErrorState
import com.example.network.InvoiceResult
import com.example.network.RequestState
import com.example.network.SuccessState
import com.example.network.services.InvoiceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateInvoiceCallback(private val number: String, private val login: String, private val liveData: MutableLiveData<RequestState<InvoiceResult>>) :
    Callback<InvoiceResponse> {

    override fun onFailure(call: Call<InvoiceResponse>, t: Throwable) {
        liveData.postValue(ErrorState(t.message.toString()))
    }

    override fun onResponse(call: Call<InvoiceResponse>, response: Response<InvoiceResponse>) {
        if (!response.isSuccessful) {
            liveData.postValue(ErrorState(response.message()))
            return
        }
        val body = response.body()
        if (body == null) {
            liveData.postValue(ErrorState("Unknown server response"))
            return
        }
        liveData.postValue(SuccessState(InvoiceResult(number, login)))
    }

}
