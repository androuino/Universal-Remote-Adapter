package com.intellisrc.universalremoteadapter.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ir_codes_table")
data class RatesEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = null,
    var currency: String? = null,
    var rate: Double? = null,
    var currencyType: String? = null
)