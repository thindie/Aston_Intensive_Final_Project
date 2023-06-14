package com.example.thindie.astonrickandmorty.data.remotesource

import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageResponse
import com.example.thindie.astonrickandmorty.data.remotesource.util.ApiParams
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PersonagesApi {


    @GET(ApiParams.CHARACTER)
    suspend fun getAllCharacters(): PersonageResponse


    @GET(ApiParams.CHARACTER_ID)
    suspend fun getSingleCharacter(
        @Path("id") characterId: Int
    ): PersonageDto

    @GET
    suspend fun getBy(@Url url: String):  PersonageResponse

    @GET(ApiParams.EPISODE_ID)
    suspend fun getMultiply(
        @Path(
            "id",
            encoded = true
        ) episodesIds: String
    ):  List<PersonageDto>

}