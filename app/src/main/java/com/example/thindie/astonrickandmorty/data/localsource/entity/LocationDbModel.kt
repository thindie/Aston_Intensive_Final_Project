package com.example.thindie.astonrickandmorty.data.localsource.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thindie.astonrickandmorty.data.localsource.DataBaseUtil

@Entity(tableName = DataBaseUtil.locations)
data class LocationDbModel(
    val created: String,
    val dimension: String,
    @PrimaryKey
    val id: Int,
    val name: String,
    val residentsList: String, //todo
    val type: String,
    val url: String
)