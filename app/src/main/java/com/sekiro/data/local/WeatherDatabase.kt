package com.sekiro.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sekiro.data.models.City

@Database(entities = [City::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}