package com.example.network

import androidx.lifecycle.LiveData

interface NetworkProvider {
    fun login(login: String, password: String): LiveData<RequestState>
}