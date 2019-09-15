package com.example.easywin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.network.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class SessionHolderImpl @Inject constructor(var networkProvider: NetworkProvider): SessionHolder {
    private var sessionSubject = BehaviorSubject.create<FastPaySession?>()
    private var previousSessionId: FastPaySession? = null

    override fun previousSession(): FastPaySession? {
        return previousSessionId
    }

    override fun makeSession(deviceId: String): LiveData<RequestState<FastPaySession>> {
        val liveData = MutableLiveData<RequestState<FastPaySession>>()
        if (deviceId != null) {
            networkProvider.makeSession(deviceId).subscribe {
                liveData.postValue(it)
                if (it is SuccessState) {
                    sessionSubject.onNext(it.payload)
                    previousSessionId = it.payload
                }
            }
        } else {
            liveData.postValue(ErrorState("Not logged in"))
        }
        return liveData
    }


}