package com.example.network

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Observable

interface NetworkProvider {
    fun login(login: String, password: String): Observable<RequestState<UserData>>

    fun makeSession(deviceId: String): Observable<RequestState<FastPaySession>>

    fun createInvoice(amount: Int, number: String, description: String): LiveData<RequestState<InvoiceResult>>
}