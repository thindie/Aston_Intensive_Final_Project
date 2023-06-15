package com.example.thindie.astonrickandmorty.data.remotesource

import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesResponse
import com.example.thindie.astonrickandmorty.data.remotesource.entity.location.LocationDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.location.LocationResponse
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageDto
import com.example.thindie.astonrickandmorty.data.remotesource.util.ApiParams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url


interface LocationApi {

    @GET(LOCATION)
    suspend fun getAllLocations(): LocationResponse


    @GET(LOCATION_ID)
    suspend fun getSingleLocation(
        @Path("id") locationId: Int
    ): LocationDto

    @GET(LOCATION_ID)
    suspend fun getMultiLocation(
        @Path("id", encoded = true) characterId: String
    ): List<LocationDto>

    @GET
    suspend fun getBy(@Url url: String): LocationResponse

    @GET
    suspend fun getByAsDto(@Url url: String): LocationDto
}


private const val ENDPOINT = "https://rickandmortyapi.com/api/"
private const val LOCATION = "${ENDPOINT}location"
private const val LOCATION_ID = "${LOCATION}/{id}"