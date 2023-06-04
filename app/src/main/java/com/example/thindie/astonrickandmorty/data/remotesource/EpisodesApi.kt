package com.example.thindie.astonrickandmorty.data.remotesource

import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesRaw
import com.example.thindie.astonrickandmorty.data.remotesource.util.ApiParams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface EpisodesApi {

    @GET(ApiParams.EPISODE)
    suspend fun getAllEpisodes(): Response<EpisodesRaw>


    @GET(ApiParams.EPISODE_ID)
    suspend fun getSingleEpisode(
        @Path("id") episodeId: Int
    ): Response<EpisodesDto>
/*
    @Suppress("LongParameterList")
    @GET(ApiParams.EPISODE)
    suspend fun getEpisodeFiltered(
        episodeLocationModifier: EpisodeQueryModifier,
        @Query(ApiParams.EPISODE_NAME) name: String = episodeLocationModifier.name,
        @Query(ApiParams.EPISODE_CODE) type: String = episodeLocationModifier.episode,
    )*/
}