package com.example.easywin.dagger

import com.example.easywin.*
import com.example.easywin.dagger.modules.DataModule
import com.example.easywin.dagger.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DataModule::class])
interface AppComponent {
    fun inject(testActivity: TestActivity)
    fun inject(loginActivity : LoginActivity)
    fun inject(createmeetActivity: CreatemeetActivity)
    fun inject(endOfCreatingMeeting: EndOfCreatingMeeting)
    fun inject(joinActivity: JoinActivity)
}