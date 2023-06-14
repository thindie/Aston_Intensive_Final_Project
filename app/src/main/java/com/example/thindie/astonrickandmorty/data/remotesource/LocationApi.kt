package com.example.thindie.astonrickandmorty.data.remotesource

import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesResponse
import com.example.thindie.astonrickandmorty.data.remotesource.entity.location.LocationDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.location.LocationResponse
import com.example.thindie.astonrickandmorty.data.remotesource.util.ApiParams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url


interface LocationApi {

    @GET(ApiParams.LOCATION)
    suspend fun getAllLocations(): LocationResponse


    @GET(ApiParams.LOCATION_ID)
    suspend fun getSingleLocation(
        @Path("id") locationId: Int
    ): LocationDto

    @GET
    suspend fun getBy(@Url url: String): LocationResponse

    @GET(ApiParams.EPISODE_ID)
    suspend fun getMultiply(
        @Path(
            "id",
            encoded = true
        ) id: Int
    ): List<LocationDto>
}


