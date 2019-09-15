package com.example.easywin.dagger

import com.example.easywin.*
import com.example.easywin.LoginActivity
import com.example.easywin.MainActivity
import com.example.easywin.TestActivity
import com.example.easywin.UserViewModel
import com.example.easywin.dagger.modules.DataModule
import com.example.easywin.dagger.modules.NetworkModule
import com.example.easywin.fragments.MainPageFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DataModule::class])
interface AppComponent {
    fun inject(testActivity: TestActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(loginActivity : LoginActivity)
    fun inject(createmeetActivity: CreatemeetActivity)
    fun inject(endOfCreatingMeeting: EndOfCreatingMeeting)
    fun inject(joinActivity: JoinActivity)
    fun inject(mainPageFragment: MainPageFragment)
    fun inject(userViewModel: UserViewModel)
    fun inject(assignTheMount: AssignTheMount)
    fun inject(activity: WaitEveryoneActivity)
}