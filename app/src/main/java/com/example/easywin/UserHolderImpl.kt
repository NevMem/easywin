package com.example.easywin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.network.*
import javax.inject.Inject

class UserHolderImpl @Inject constructor(private val networkProvider: NetworkProvider) : UserHolder {

    private var user: UserData? = null

    override fun currentUser(): UserData? = user

    override fun tryLogin(login: String, password: String): LiveData<RequestState<UserData>> {
        val liveData = MutableLiveData<RequestState<UserData>>()
        networkProvider.login(login, password).subscribe {
            liveData.postValue(it)
            if (it is SuccessState) {
                user = it.payload
            }
        }
        return liveData
    }
}