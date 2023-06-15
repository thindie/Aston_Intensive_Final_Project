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

    @GET(CHARACTER_ID)
    suspend fun getMultiCharacter(
        @Path("id", encoded = true) characterId: String
    ): List<PersonageDto>

    @GET
    suspend fun getBy(@Url url: String): PersonageResponse

    @GET
    suspend fun getByAsDto(@Url url: String): PersonageDto
}

private const val ENDPOINT = "https://rickandmortyapi.com/api/"
private const val CHARACTER = "${ENDPOINT}character"
private const val CHARACTER_ID = "${CHARACTER}/{id}"
