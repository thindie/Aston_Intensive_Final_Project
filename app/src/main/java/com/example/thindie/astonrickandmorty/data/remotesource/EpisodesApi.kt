package com.example.thindie.astonrickandmorty.data.remotesource

import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface EpisodesApi {

    @GET(EPISODE)
    suspend fun getAllEpisodes(): EpisodesResponse

    @GET(EPISODE_ID)
    suspend fun getMultiEpisode(
        @Path("id", encoded = true) characterId: String,
    ): List<EpisodesDto>

    @GET(EPISODE_ID)
    suspend fun getSingleEpisode(
        @Path("id") episodeId: Int,
    ): EpisodesDto

    @GET(EPISODE)
    suspend fun getFilteredEpisodes(
        @Query(QUERY_PARAM_NAME) name: String,
        @Query(QUERY_PARAM_EPISODE) episode: String,
    ): EpisodesResponse

    @GET
    suspend fun getBy(@Url url: String): EpisodesResponse
}

private const val ENDPOINT = "https://rickandmortyapi.com/api/"
private const val EPISODE = "${ENDPOINT}episode"
private const val EPISODE_ID = "$EPISODE/{id}"
private const val QUERY_PARAM_NAME = "name"
private const val QUERY_PARAM_EPISODE = "episode"
