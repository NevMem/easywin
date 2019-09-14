package com.example.easywin.dagger.modules

import com.example.network.NetworkProvider
import com.example.network.NetworkProviderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun providesNetworkProvider(): NetworkProvider = NetworkProviderImpl()
}