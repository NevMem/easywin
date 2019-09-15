package com.example.easywin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.network.*
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomHolderImpl
        @Inject constructor(private val userHolder: UserHolder, private val networkProvider: NetworkProvider)
        : RoomHolder {

    var state = RoomHolderState.MANAGE

    init {
        GlobalScope.launch {
            while (true) {
                val roomId = currentRoomId
                if (roomId != null) {
                    networkProvider.loadRoomInfo(roomId).subscribe {
                        if (it is SuccessState && it.payload.roomId == currentRoomId) {
                            currentRoom.onNext(it.payload)
                        }
                    }
                }
                delay(1000)
            }
        }
    }

    private val currentRoom = BehaviorSubject.create<RoomInfo>()
    private var currentRoomId: Int? = null

    override fun currentRoom(): LiveData<RoomInfo> {
        val liveData = MutableLiveData<RoomInfo>()

        currentRoom.subscribe {
            liveData.postValue(it)
        }

        return liveData
    }

    override fun currentRoomId(): Int? {
        return currentRoomId
    }

    override fun createRoom(roomName: String): LiveData<RequestState<RoomInfo>> {
        state = RoomHolderState.MANAGE
        val user = userHolder.currentUser()
        val liveData = MutableLiveData<RequestState<RoomInfo>>()
        if (user == null) {
            liveData.postValue(ErrorState("Not logged in"))
        } else {
            networkProvider.createRoom(user.login.toString(), roomName).subscribe {
                liveData.postValue(it)
                if (it is SuccessState) {
                    currentRoomId = it.payload.roomId
                    currentRoom.onNext(it.payload)
                }
            }
        }
        return liveData
    }

    override fun join(roomId: Int): LiveData<RequestState<RoomInfo>> {
        state = RoomHolderState.JOINED
        val user = userHolder.currentUser()
        val liveData = MutableLiveData<RequestState<RoomInfo>>()
        if (user == null) {
            liveData.postValue(ErrorState("Not logged in"))
        } else {
            networkProvider.join(user.login.toString(), roomId).subscribe {
                liveData.postValue(it)
                if (it is SuccessState) {
                    currentRoomId = it.payload.roomId
                    currentRoom.onNext(it.payload)
                }
            }
        }
        return liveData
    }

    override fun state(): RoomHolderState = state

    override fun gotoPickMoney() {
        networkProvider.gotoPickMoney(currentRoomId ?: -1)
    }

    override fun gotoLastStage() {
        networkProvider.gotoLastStage(currentRoomId ?: -1)
    }
}