package com.example.thindie.astonrickandmorty.data.localsource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.thindie.astonrickandmorty.data.localsource.entity.EpisodeDbModel

@Dao
interface EpisodesDao {

    @Query("SELECT * FROM $EPISODES")
    suspend fun getAllEpisodes(): List<EpisodeDbModel>

    @Query("SELECT * FROM $EPISODES WHERE id=:id LIMIT 1")
    suspend fun getConcreteEpisode(id: Int): EpisodeDbModel

    @Upsert
    suspend fun upsertEpisodes(things: List<EpisodeDbModel>)

    @Query("SELECT * FROM $EPISODES WHERE name=:name")
    suspend fun getFilteredEpisodesByName(name: String): List<EpisodeDbModel>

    @Query("SELECT * FROM $EPISODES WHERE episode=:episode")
    suspend fun getFilteredEpisodesByEpisode(episode: String): List<EpisodeDbModel>
}

private const val EPISODES = "episodes"
