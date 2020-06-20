package com.intellisrc.universalremoteadapter.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.intellisrc.universalremoteadapter.room.entities.RatesEntity

@Dao
interface RatesDao {
    @androidx.room.Query("SELECT * from ir_codes_table ORDER BY id ASC")
    fun getRates(): LiveData<List<RatesEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(ratesEntityList: RatesEntity)

    @Update(onConflict = REPLACE)
    fun update(ratesEntity: RatesEntity)

    @Query("select count(*) from ir_codes_table")
    fun countAll(): Int
}