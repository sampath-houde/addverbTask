package com.example.addverbtask.database.repo

import androidx.lifecycle.LiveData
import com.example.addverbtask.data.RegionResponse
import com.example.addverbtask.database.dao.RegionDao
import com.example.addverbtask.database.entities.RegionOffline

class RegionOfflineRepo(private val regionDao: RegionDao) {

    val readAllCountries: LiveData<RegionOffline>
        get() = regionDao.readAllCountries()


    suspend fun addNewUser(user: RegionOffline) {
        regionDao.addCountries(user)
    }
}