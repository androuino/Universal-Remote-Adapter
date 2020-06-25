package com.intellisrc.universalremoteadapter.room.daos

import androidx.lifecycle.LiveData
import com.intellisrc.universalremoteadapter.room.entities.CodesEntity

interface CodesDaoInterface {
    fun getCodes(): LiveData<List<CodesEntity>>
    fun insert(codesEntity: CodesEntity)
    fun update(codesEntity: CodesEntity)
    fun countAll(): Int
}