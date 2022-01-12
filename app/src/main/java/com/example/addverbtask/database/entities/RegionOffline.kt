package com.example.addverbtask.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.addverbtask.data.RegionResponseList
import com.task.krishinetwork.utils.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class RegionOffline(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val reponse: List<RegionResponseList>
)
