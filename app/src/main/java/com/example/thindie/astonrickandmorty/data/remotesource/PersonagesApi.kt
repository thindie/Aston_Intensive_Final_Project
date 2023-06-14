package com.example.thindie.astonrickandmorty.data.remotesource

import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageResponse
import com.example.thindie.astonrickandmorty.data.remotesource.util.ApiParams
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PersonagesApi {


    @GET(CHARACTER)
    suspend fun getAllCharacters(): PersonageResponse


    @GET(CHARACTER_ID)
    suspend fun getSingleCharacter(
        @Path("id") characterId: Int
    ): PersonageDto

    @GET
    suspend fun getBy(@Url url: String):  PersonageResponse

    @GET(CHARACTER_ID)
    suspend fun getMultiply(
        @Path(
            "id",
            encoded = false
        ) id: Int
    ):  List<PersonageDto>

}

private const val ENDPOINT = "https://rickandmortyapi.com/api/"
private const val CHARACTER = "${ENDPOINT}character"
private const val LOCATION = "${ENDPOINT}location"
private const val EPISODE = "${ENDPOINT}episode"
private const val EPISODE_ID = "${EPISODE}/{id},"
private const val CHARACTER_ID = "${CHARACTER}/{id},"
private const val LOCATION_ID = "${LOCATION}/{id},"