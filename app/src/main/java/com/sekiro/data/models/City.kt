package com.sekiro.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class City(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "country") val country: String? = null,
    @ColumnInfo(name = "state") val state: String? = null,
    @ColumnInfo(name = "latitude") val lat: Float? = null,
    @ColumnInfo(name = "longitude") val lon: Float? = null,
) : Parcelable {

    fun getFullName() = listOfNotNull(name, state, country)
        .joinToString(", ")
}