package com.intellisrc.universalremoteadapter.room.daos

import androidx.lifecycle.LiveData
import com.intellisrc.universalremoteadapter.room.entities.RatesEntity

interface RatesDaoInterface {
    fun getRates(): LiveData<List<RatesEntity>>
    fun insert(ratesEntity: RatesEntity)
    fun update(ratesEntity: RatesEntity)
    fun countAll(): Int
}