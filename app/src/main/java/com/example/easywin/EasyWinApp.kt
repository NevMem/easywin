package com.example.easywin

import android.app.Application
import com.example.easywin.dagger.AppComponent
import com.example.easywin.dagger.DaggerAppComponent
import com.example.easywin.dagger.modules.NetworkModule

class EasyWinApp : Application() {
    val appComponent: AppComponent = DaggerAppComponent.builder()
        .networkModule(NetworkModule())
        .build()
}