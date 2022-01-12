package com.example.addverbtask.ui.viewmodel

import androidx.lifecycle.*
import com.example.addverbtask.data.RegionOnlineRepo
import com.example.addverbtask.data.RegionResponse
import com.example.addverbtask.database.entities.RegionOffline
import com.example.addverbtask.database.repo.RegionOfflineRepo
import com.example.addverbtask.utils.ApiResponseHandler
import kotlinx.coroutines.launch

class RegionViewModel(private val offlineRepo: RegionOfflineRepo, private val onlineRepo: RegionOnlineRepo)
    : ViewModel(){

    //Offline
    var readAllCountries: LiveData<RegionOffline> = offlineRepo.readAllCountries

    fun addCountries(countries: RegionOffline) = viewModelScope.launch {
        offlineRepo.addCountries(countries)
    }

    //Online
    private val _countriesListOnline: MutableLiveData<ApiResponseHandler<RegionResponse>> = MutableLiveData()

    val countriesListOnline: LiveData<ApiResponseHandler<RegionResponse>>
        get() = _countriesListOnline

    fun getCountriesFromRegionApi() = viewModelScope.launch {
        _countriesListOnline.value = onlineRepo.getCountries()
    }


}