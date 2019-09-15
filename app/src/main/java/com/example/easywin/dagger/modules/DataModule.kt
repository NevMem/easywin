package com.example.easywin.dagger.modules

import com.example.easywin.*
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


    @Singleton
    @Provides
    fun providesCurrentManagingRoomHodler(impl: RoomHolderImpl): RoomHolder = impl
}