package com.example.easywin.dagger

import com.example.easywin.LoginActivity
import com.example.easywin.TestActivity
import com.example.easywin.dagger.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(testActivity: TestActivity)
    fun inject(loginActivity : LoginActivity)
}