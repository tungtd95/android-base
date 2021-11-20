package com.sekiro.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sekiro.data.models.City
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface WeatherDao {

    @Query("SELECT * FROM city")
    fun getCitiesFlow(): Flowable<List<City>>

    @Query("SELECT * FROM city")
    fun getCitiesOneShot(): Single<List<City>>

    @Query("SELECT * FROM city WHERE id=:cityId")
    fun getCityById(cityId: Int): Maybe<City>

    @Insert
    fun add(city: City): Completable

    @Delete
    fun remove(city: City): Completable
}