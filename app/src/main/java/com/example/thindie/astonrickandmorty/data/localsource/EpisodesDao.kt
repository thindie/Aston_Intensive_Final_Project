package com.example.thindie.astonrickandmorty.data.localsource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.thindie.astonrickandmorty.data.localsource.entity.EpisodeDbModel


@Dao
interface EpisodesDao {

    @Query("SELECT * FROM ${DataBaseUtil.episodes}")
    suspend fun getAllEpisodes(): List<EpisodeDbModel>

    @Query("SELECT * FROM ${DataBaseUtil.episodes} WHERE id=:id LIMIT 1")
    suspend fun getConcreteEpisode(id: Int): EpisodeDbModel

    @Upsert
    suspend fun upsertEpisodes(things: List<EpisodeDbModel>)
}