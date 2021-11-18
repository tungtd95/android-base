package com.sekiro.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "country") val country: String? = null,
    @ColumnInfo(name = "state") val state: String? = null
) {

    fun getFullName() = listOfNotNull(name, state, country)
        .joinToString(", ")
}