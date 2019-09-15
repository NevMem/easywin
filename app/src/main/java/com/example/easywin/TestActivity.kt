package com.example.easywin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import com.example.network.*
import kotlinx.android.synthetic.main.test_activity.*
import javax.inject.Inject

class TestActivity : AppCompatActivity() {
    @Inject
    lateinit var networkProvider: NetworkProvider

    @Inject
    lateinit var userHolder: UserHolder

    @Inject
    lateinit var sessionHolder: SessionHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as EasyWinApp).appComponent.inject(this)

        setContentView(R.layout.test_activity)

        loginButton.setOnClickListener {
            val login = login.text.toString()
            val password = password.text.toString()
            userHolder.tryLogin(login, password).observe(this, Observer {
                when (it) {
                    is PendingState<UserData> -> progress.visibility = View.VISIBLE
                    is ErrorState<UserData> -> {
                        progress.visibility = View.GONE
                        out_text.text = it.error
                    }
                    is SuccessState<UserData> -> {
                        progress.visibility = View.GONE
                        val userData = it.payload
                        out_text.text = "${userData.name} ${userData.login} ${userData.age} ${userData.surname}"
                    }
                }
            })
        }
    }
}
