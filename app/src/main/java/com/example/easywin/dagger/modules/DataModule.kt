package com.example.easywin.dagger.modules

import com.example.easywin.SessionHolder
import com.example.easywin.SessionHolderImpl
import com.example.easywin.UserHolder
import com.example.easywin.UserHolderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun providesUserHolder(impl: UserHolderImpl): UserHolder = impl

    @Singleton
    @Provides
    fun providesSessionHolder(impl: SessionHolderImpl): SessionHolder = impl
}