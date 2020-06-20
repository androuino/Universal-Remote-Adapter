package com.intellisrc.universalremoteadapter.di.modules

import android.content.Context
import com.intellisrc.universalremoteadapter.room.RoomDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(private val context: Context) {
    @Provides
    @Singleton
    fun providesRoomDataSource() = RoomDataSource.buildPersistent(context)
}