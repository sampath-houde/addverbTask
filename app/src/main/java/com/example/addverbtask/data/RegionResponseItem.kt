package com.example.addverbtask.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.task.krishinetwork.utils.Constants


class RegionResponse : ArrayList<RegionResponseList>()

data class RegionResponseList(
    val borders: List<String>,
    val capital: List<String>,
    val flag: String,
    val flags: Flags,
    val name: Name,
    val population: Int,
    val region: String,
    val subregion: String,
)

data class Name(
    val common: String,
)
data class Flags(
    val png: String,
    val svg: String
)