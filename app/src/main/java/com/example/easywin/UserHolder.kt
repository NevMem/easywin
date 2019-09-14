package com.example.easywin

import androidx.lifecycle.LiveData
import com.example.network.RequestState
import com.example.network.UserData

interface UserHolder {
    fun currentUser(): UserData?

    fun tryLogin(login: String, password: String): LiveData<RequestState<UserData>>
}