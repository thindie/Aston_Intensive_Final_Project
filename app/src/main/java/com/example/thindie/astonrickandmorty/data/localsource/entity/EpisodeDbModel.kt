package com.example.thindie.astonrickandmorty.data.localsource.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thindie.astonrickandmorty.domain.LinkPool

private const val EPISODES = "episodes"

@Entity(tableName = EPISODES)
data class EpisodeDbModel(
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    @PrimaryKey
    val id: Int,
    val name: String,
    val url: String,
    val pool: LinkPool,
)
