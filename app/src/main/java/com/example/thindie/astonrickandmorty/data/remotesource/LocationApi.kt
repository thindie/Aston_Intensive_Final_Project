package com.example.thindie.astonrickandmorty.data.remotesource

import com.example.thindie.astonrickandmorty.data.remotesource.entity.location.LocationDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.location.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
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

    @GET(LOCATION)
    suspend fun getFilteredLocations(
        @Query(QUERY_PARAM_NAME) name: String,
        @Query(QUERY_PARAM_TYPE) episode: String,
        @Query(QUERY_PARAM_DIMENSION) dimension: String
    ): LocationResponse
}

private const val ENDPOINT = "https://rickandmortyapi.com/api/"
private const val LOCATION = "${ENDPOINT}location"
private const val LOCATION_ID = "$LOCATION/{id}"
private const val QUERY_PARAM_NAME = "name"
private const val QUERY_PARAM_TYPE = "type"
private const val QUERY_PARAM_DIMENSION = "dimension"
