package com.example.addverbtask.utils

import android.content.Context
import android.widget.Toast
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.room.TypeConverter
import com.example.addverbtask.data.Flags
import com.example.addverbtask.data.Name
import com.example.addverbtask.data.RegionResponseList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun isInternetAvailable(context: Context): Boolean {
    var isConnected = false // Initial Value
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}


class RegionListConverter {
    @TypeConverter
    fun fromRegionList(data: List<RegionResponseList>) : String {

        return Gson().toJson(data)
    }

    @TypeConverter
    fun toRegionList(data: String) : List<RegionResponseList> {
        val listType: Type = object : TypeToken<Flags>() {}.type
        return Gson().fromJson(data, listType)
    }

}

