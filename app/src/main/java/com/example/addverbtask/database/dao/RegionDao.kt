package com.example.addverbtask.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.addverbtask.data.RegionResponseList
import com.example.addverbtask.database.entities.RegionOffline

@Dao
interface RegionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCountries(country: RegionOffline)

    @Query("SELECT * FROM country_table ORDER BY id DESC")
    fun readAllCountries(): LiveData<RegionOffline>

    @Delete
    suspend fun delete(country: RegionOffline)

}