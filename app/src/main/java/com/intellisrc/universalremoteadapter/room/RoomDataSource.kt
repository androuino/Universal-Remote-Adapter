package com.intellisrc.universalremoteadapter.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.intellisrc.universalremoteadapter.Constants
import com.intellisrc.universalremoteadapter.room.daos.CodesDao
import com.intellisrc.universalremoteadapter.room.entities.CodesEntity

@Database(entities = [
    CodesEntity::class
], version = 1)
abstract class RoomDataSource : RoomDatabase() {
    abstract fun codesDao(): CodesDao

    companion object {
        fun buildPersistent(context: Context): RoomDataSource = Room.databaseBuilder(
            context.applicationContext,
            RoomDataSource::class.java,
            Constants.DATABASE_NAME
        ).allowMainThreadQueries().build()
    }
}