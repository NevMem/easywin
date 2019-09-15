package com.example.easywin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject

class UserViewModel(app: Application): AndroidViewModel(app) {
    @Inject
    lateinit var userHolder: UserHolder

    init {
        (app as EasyWinApp).appComponent.inject(this)
    }
}