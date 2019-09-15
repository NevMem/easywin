package com.example.easywin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.user_row.view.*
import kotlinx.android.synthetic.main.wait_everyone.*
import kotlinx.android.synthetic.main.wait_row.view.*
import javax.inject.Inject

class WaitEveryoneActivity : AppCompatActivity() {
    val list = ArrayList<View>()

    @Inject
    lateinit var roomHolder: RoomHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wait_everyone)

        (applicationContext as EasyWinApp).appComponent.inject(this)

        val layoutInflater = LayoutInflater.from(this)

        roomHolder.currentRoom().observe(this, Observer {
            if (list.isEmpty()) {
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
}
