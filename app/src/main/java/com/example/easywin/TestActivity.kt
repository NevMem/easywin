package com.example.easywin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.network.*
import kotlinx.android.synthetic.main.test_activity.*
import javax.inject.Inject

class TestActivity : AppCompatActivity() {
    @Inject
    lateinit var networkProvider: NetworkProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as EasyWinApp).appComponent.inject(this)

        setContentView(R.layout.test_activity)

        loginButton.setOnClickListener {
            val login = login.text.toString()
            val password = password.text.toString()
            networkProvider.login(login, password).observe(this, Observer {
                when (it) {
                    is PendingState -> progress.visibility = View.VISIBLE
                    is ErrorState -> {
                        progress.visibility = View.GONE
                        out_text.text = it.error
                    }
                    is SuccessState<*> -> {
                        progress.visibility = View.GONE
                        val userData = it.payload as UserData
                        out_text.text = "${userData.name} ${userData.login} ${userData.age} ${userData.surname}"
                    }
                }
            })
        }
    }
}
