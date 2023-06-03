package com.example.thindie.astonrickandmorty.data.remotesource

import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageResponse
import com.example.thindie.astonrickandmorty.data.remotesource.util.ApiParams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PersonagesApi {


    @GET(ApiParams.CHARACTER)
    suspend fun getAllCharacters(): Response<PersonageResponse>


    @GET(ApiParams.CHARACTER_ID)
    suspend fun getSingleCharacter(
        @Path("id") characterId: Int
    ): Response<PersonageDto>

}