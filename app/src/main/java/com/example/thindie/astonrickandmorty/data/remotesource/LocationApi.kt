package com.example.thindie.astonrickandmorty.data.remotesource

import com.example.thindie.astonrickandmorty.data.remotesource.entity.location.LocationDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.location.LocationRaw
import com.example.thindie.astonrickandmorty.data.remotesource.util.ApiParams
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url


interface LocationApi {


    @GET(ApiParams.LOCATION)
    suspend fun getAllLocations(): Response<LocationRaw>

    @GET
    suspend fun getAllCharacterWithPage(@Url urlWithPage: String): JsonObject

    @GET(ApiParams.LOCATION_ID)
    suspend fun getSingleLocation(
        @Path("id") locationId: Int
    ): Response<LocationDto>

/*
    @Suppress("LongParameterList")
    @GET(ApiParams.CHARACTER)
    suspend fun getCharactersFiltered(
        characterQueryModifier: CharacterQueryModifier,
        @Query(ApiParams.CHARACTER_NAME) name: String = characterQueryModifier.name,
        @Query(ApiParams.CHARACTER_STATUS) status: String = characterQueryModifier.status,
        @Query(ApiParams.CHARACTER_SPECIES) species: String = characterQueryModifier.species,
        @Query(ApiParams.CHARACTER_TYPE) type: String = characterQueryModifier.type,
        @Query(ApiParams.CHARACTER_GENDER) gender: String = characterQueryModifier.gender,
    )
*/

}


