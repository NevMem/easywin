package com.example.easywin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.network.NetworkProvider
import com.example.network.RoomInfo
import com.example.network.SuccessState
import com.example.network.Utils
import kotlinx.android.synthetic.main.user_row.view.*
import kotlinx.android.synthetic.main.wait_everyone.*
import kotlinx.android.synthetic.main.wait_row.view.*
import javax.inject.Inject

class WaitEveryoneActivity : AppCompatActivity() {
    val list = ArrayList<View>()

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
            if (list.isEmpty()) {
                initializeInvoices(it)

                it.users.forEach { user ->
                    val view = layoutInflater.inflate(R.layout.wait_row, infoAnchor, false)
                    infoAnchor.addView(view)
                }
            }

            it.users.forEachIndexed { index, user ->
                list[index].name.text = user.name
                list[index].amount.text = user.amount.toString()

                if (user.payed!!) {
                    list[index].progress.visibility = View.VISIBLE
                    list[index].success.visibility = View.GONE
                } else {
                    list[index].progress.visibility = View.GONE
                    list[index].success.visibility = View.VISIBLE
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
                            roomInfo.roomName) }
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
