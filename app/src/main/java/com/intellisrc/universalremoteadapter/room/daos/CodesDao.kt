package com.intellisrc.universalremoteadapter.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.intellisrc.universalremoteadapter.room.entities.CodesEntity

@Dao
interface CodesDao {
    @androidx.room.Query("SELECT * from ir_codes_table ORDER BY id ASC")
    fun getCodes(): LiveData<List<CodesEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(codesEntityList: CodesEntity)

    @Update(onConflict = REPLACE)
    fun update(codesEntity: CodesEntity)

    @Query("select count(*) from ir_codes_table")
    fun countAll(): Int
}