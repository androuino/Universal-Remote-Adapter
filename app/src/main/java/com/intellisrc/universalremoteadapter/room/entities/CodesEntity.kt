package com.intellisrc.universalremoteadapter.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ir_codes_table")
data class CodesEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = null,
    var name: String? = null,
    var code: String? = null
)