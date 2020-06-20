package com.intellisrc.universalremoteadapter.room.daos

import androidx.lifecycle.LiveData
import com.intellisrc.universalremoteadapter.room.entities.RatesEntity
import javax.inject.Inject

class RatesDaoImpl @Inject constructor(private val ratesDao: RatesDao) : RatesDaoInterface {
    override fun getRates(): LiveData<List<RatesEntity>> {
        return ratesDao.getRates()
    }

    override fun insert(ratesEntity: RatesEntity) {
        ratesDao.insert(ratesEntity)
    }

    override fun update(ratesEntity: RatesEntity) {
        ratesDao.update(ratesEntity)
    }

    override fun countAll(): Int {
        return ratesDao.countAll()
    }
}