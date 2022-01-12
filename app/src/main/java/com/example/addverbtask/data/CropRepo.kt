package com.example.addverbtask.data

import com.example.addverbtask.api.RegionApi
import com.example.addverbtask.utils.BaseRepository

class RegionOnlineRepo(private val api: RegionApi) : BaseRepository() {

    suspend fun getCountries() = safeApiCall {
        api.filterCountriesFromRegion()
    }

}