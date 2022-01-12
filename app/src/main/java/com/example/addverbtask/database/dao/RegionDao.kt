package com.example.addverbtask.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.addverbtask.data.RegionResponseList
import com.example.addverbtask.database.entities.RegionOffline

interface RegionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCountries(country: RegionOffline)


    @Query("SELECT * FROM country_table ORDER BY id DESC")
    fun readAllCountries(): LiveData<RegionOffline>

}