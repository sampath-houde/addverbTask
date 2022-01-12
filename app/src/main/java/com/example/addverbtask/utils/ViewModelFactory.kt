package com.example.addverbtask.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.addverbtask.data.RegionOnlineRepo
import com.example.addverbtask.database.repo.RegionOfflineRepo
import com.example.addverbtask.ui.viewmodel.RegionViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val onlineRepo: BaseRepository,
    private val offlineRepo: RegionOfflineRepo
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegionViewModel::class.java) -> RegionViewModel(offlineRepo, onlineRepo = onlineRepo as RegionOnlineRepo) as T
            else -> throw IllegalArgumentException("ViewModel class not found")
        }

    }

}