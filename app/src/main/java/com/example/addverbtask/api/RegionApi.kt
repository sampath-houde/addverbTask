package com.example.addverbtask.api

import com.example.addverbtask.data.RegionResponse
import retrofit2.http.GET

interface RegionApi {

    @GET("region/asia")
    suspend fun filterCountriesFromRegion() : RegionResponse
}