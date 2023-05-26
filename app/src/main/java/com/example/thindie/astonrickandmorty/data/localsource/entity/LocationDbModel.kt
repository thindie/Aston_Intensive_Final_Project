package com.example.thindie.astonrickandmorty.data.localsource.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thindie.astonrickandmorty.domain.LinkPool
private const val LOCATIONS = "locations"

@Entity(tableName = LOCATIONS)
data class LocationDbModel(
    val created: String,
    val dimension: String,
    @PrimaryKey
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String,
    val pool: LinkPool
)
