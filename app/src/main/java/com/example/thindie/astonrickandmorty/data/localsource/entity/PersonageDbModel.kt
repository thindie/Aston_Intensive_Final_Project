package com.example.thindie.astonrickandmorty.data.localsource.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thindie.astonrickandmorty.data.localsource.DataBaseUtil

@Entity(tableName = DataBaseUtil.personages)
data class PersonageDbModel(
    @PrimaryKey
    val id: Int,
    val created: String,
    val episodesList: String, // detail screen
    val gender: String, // filter + screen
    val image: String, // + screen
    val location: String, // detail screen
    val name: String, // filter + screen
    val origin: String,  // detail screen
    val species: String, // filter + screen
    val status: String, // filter + screen
    val type: String, // filter
    val url: String
)