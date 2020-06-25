package com.intellisrc.universalremoteadapter.room.daos

import androidx.lifecycle.LiveData
import com.intellisrc.universalremoteadapter.room.entities.CodesEntity
import javax.inject.Inject

class CodesDaoImpl @Inject constructor(private val codesDao: CodesDao) : CodesDaoInterface {
    override fun getCodes(): LiveData<List<CodesEntity>> {
        return codesDao.getCodes()
    }

    override fun insert(codesEntity: CodesEntity) {
        codesDao.insert(codesEntity)
    }

    override fun update(codesEntity: CodesEntity) {
        codesDao.update(codesEntity)
    }

    override fun countAll(): Int {
        return codesDao.countAll()
    }
}