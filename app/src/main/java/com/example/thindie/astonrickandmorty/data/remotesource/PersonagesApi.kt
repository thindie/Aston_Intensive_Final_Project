package com.example.thindie.astonrickandmorty.data.remotesource

import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PersonagesApi {

    @GET(CHARACTER)
    suspend fun getAllCharacters(): PersonageResponse

    @GET(CHARACTER_ID)
    suspend fun getSingleCharacter(
        @Path("id") characterId: Int,
    ): PersonageDto

    @GET(CHARACTER_ID)
    suspend fun getMultiCharacter(
        @Path("id", encoded = true) characterId: String,
    ): List<PersonageDto>

    @GET(CHARACTER)
    suspend fun getFilteredPersonages(
        @Query(QUERY_PARAM_NAME) name: String,
        @Query(QUERY_PARAM_STATUS) status: String,
        @Query(QUERY_PARAM_SPECIES) species: String,
        @Query(QUERY_PARAM_TYPE) type: String,
        @Query(QUERY_PARAM_GENDER) gender: String,
    ): PersonageResponse

    @GET
    suspend fun getBy(@Url url: String): PersonageResponse
}

private const val ENDPOINT = "https://rickandmortyapi.com/api/"
private const val CHARACTER = "${ENDPOINT}character"
private const val CHARACTER_ID = "$CHARACTER/{id}"
private const val QUERY_PARAM_NAME = "name"
private const val QUERY_PARAM_STATUS = "status"
private const val QUERY_PARAM_SPECIES = "species"
private const val QUERY_PARAM_TYPE = "type"
private const val QUERY_PARAM_GENDER = "gender"
