package com.example.thindie.astonrickandmorty.data.remotesource

import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesResponse
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageDto
import com.example.thindie.astonrickandmorty.data.remotesource.util.ApiParams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url


interface EpisodesApi {

    @GET(EPISODE)
    suspend fun getAllEpisodes(): EpisodesResponse

    @GET(EPISODE_ID)
    suspend fun getMultiEpisode(
        @Path("id", encoded = true) characterId: String
    ): List<EpisodesDto>

    @GET(EPISODE_ID)
    suspend fun getSingleEpisode(
        @Path("id") episodeId: Int
    ):  EpisodesDto

    @GET
    suspend fun getBy(@Url url: String): EpisodesResponse

    @GET
    suspend fun getByAsDto(@Url url: String): EpisodesDto

}

private const val ENDPOINT = "https://rickandmortyapi.com/api/"
private const val EPISODE = "${ENDPOINT}episode"
private const val EPISODE_ID = "${EPISODE}/{id}"
