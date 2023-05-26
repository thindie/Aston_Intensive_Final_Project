package com.example.thindie.astonrickandmorty.data.remotesource.entity.episode

import com.google.gson.annotations.SerializedName

data class EpisodesDto(
    @SerializedName("air_date")
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)
