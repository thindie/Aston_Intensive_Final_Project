package com.example.thindie.astonrickandmorty.data.remotesource

import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesResponse
import com.example.thindie.astonrickandmorty.data.remotesource.util.ApiParams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url


interface EpisodesApi {

    @GET(ApiParams.EPISODE)
    suspend fun getAllEpisodes(): EpisodesResponse


    @GET(ApiParams.EPISODE_ID)
    suspend fun getSingleEpisode(
        @Path("id") episodeId: Int
    ):  EpisodesDto

    @GET
    suspend fun getBy(@Url url: String): EpisodesResponse



    @GET(ApiParams.EPISODE_ID)
    suspend fun getMultiply(
        @Path(
            "id",
            encoded = true
        ) episodesIds: String
    ): List<EpisodesDto>
}