package com.example.easywin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.network.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class SessionHolderImpl @Inject constructor(var networkProvider: NetworkProvider, var userHolder: UserHolder): SessionHolder {
    private var sessionSubject = BehaviorSubject.create<FastPaySession?>()

    init {
        val user = userHolder.currentUser()
        val deviceId = user?.deviceId
        if (deviceId != null) {
            lateinit var disposable: Disposable
            disposable = networkProvider.makeSession(deviceId).subscribe {
                when (it) {
                    is SuccessState -> {
                        sessionSubject.onNext(it.payload)
                    }
                    is ErrorState -> {
                        disposable.dispose()
                    }
                }
            }
        }
    }

    override fun makeSession(): LiveData<RequestState<FastPaySession>> {
        val liveData = MutableLiveData<RequestState<FastPaySession>>()
        val deviceId = userHolder.currentUser()?.deviceId
        if (deviceId != null) {
            networkProvider.makeSession(deviceId).subscribe {
                liveData.postValue(it)
                if (it is SuccessState) {
                    sessionSubject.onNext(it.payload)
                }
            }
        } else {
            liveData.postValue(ErrorState("Not logged in"))
        }
        return liveData
    }
}