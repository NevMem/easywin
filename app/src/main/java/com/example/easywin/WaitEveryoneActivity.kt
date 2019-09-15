package com.example.easywin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.network.*
import com.example.network.services.InvoiceInfoResponce
import kotlinx.android.synthetic.main.user_row.view.*
import kotlinx.android.synthetic.main.wait_everyone.*
import kotlinx.android.synthetic.main.wait_row.view.*
import javax.inject.Inject

class WaitEveryoneActivity : AppCompatActivity() {
    val list = ArrayList<View>()
    @SuppressLint("UseSparseArrays")
    val map = HashMap<Int, LiveData<RequestState<InvoiceInfoResponce>>>()

    @Inject
    lateinit var userHolder: UserHolder

    @Inject
    lateinit var roomHolder: RoomHolder

    @Inject
    lateinit var sessionHolder: SessionHolder

    @Inject
    lateinit var networkProvider: NetworkProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wait_everyone)

        (applicationContext as EasyWinApp).appComponent.inject(this)

        val layoutInflater = LayoutInflater.from(this)

        roomHolder.currentRoom().observe(this, Observer {
            if (it == null)
                return@Observer
            if (list.isEmpty()) {
                initializeInvoices(it)

                it.users.forEach { user ->
                    val view = layoutInflater.inflate(R.layout.wait_row, infoAnchor, false)
                    infoAnchor.addView(view)
                    list.add(view)
                }
            }

            it.users.forEachIndexed { index, user ->
                list[index].userName.text = user.name
                list[index].amount.text = user.amount.toString()

                if (user.invoiceNumber != null && !map.containsKey(index)) {
                    val liveData = networkProvider.getInvoiceState(810, user.invoiceNumber!!, it.owner.deviceId!!)
                    map[index] = liveData
                    liveData.observe(this, Observer {
                        list[index].progress.visibility = View.GONE
                        list[index].success.visibility = View.VISIBLE
                        if (it is SuccessState) {
                            list[index].progress.visibility = View.VISIBLE
                            list[index].success.visibility = View.GONE
                        }
                    })
                }
            }
        })
    }

    private fun initializeInvoices(roomInfo: RoomInfo) {
        val invoices = ArrayList<String>()
        sessionHolder.makeSession(userHolder.currentUser()!!.deviceId!!)
            .observe(this, Observer { reqIt ->
                if (reqIt is SuccessState) {
                    roomInfo.users
                        .filter { it.login != roomInfo.owner.login }
                        .map { networkProvider.createInvoice(
                            sessionHolder.previousSession()!!.sessionId,
                            it.deviceId!!,
                            roomInfo.owner.deviceId!!,
                            it.amount!!,
                            Utils.createRandomString(),
                            roomInfo.roomName,
                            it.login!!) }
                        .forEach { curIt ->
                            curIt.observe(this, Observer {
                                 if (it is SuccessState) {
                                     for (userData in roomInfo.users) {
                                         if (userData.login == it.payload.login) {
                                             userData.invoiceNumber = it.payload.invoiceNumber
                                         }
                                     }
                                     if (invoices.size == roomInfo.users.size) {
                                         networkProvider.updateServerInfo(roomInfo.roomId, roomInfo)
                                     }
                                 }
                        }) }
                }
            })
    }
}
