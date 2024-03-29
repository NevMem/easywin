package com.example.easywin

import androidx.lifecycle.LiveData
import com.example.network.FastPaySession
import com.example.network.RequestState

interface SessionHolder {
    fun makeSession(deviceId: String): LiveData<RequestState<FastPaySession>>

    fun previousSession(): FastPaySession?
}