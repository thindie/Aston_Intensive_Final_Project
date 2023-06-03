package com.example.thindie.astonrickandmorty.data.remotesource

import com.example.thindie.astonrickandmorty.data.remotesource.entity.location.LocationDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.location.LocationResponse
import com.example.thindie.astonrickandmorty.data.remotesource.util.ApiParams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface LocationApi {

    @GET(ApiParams.LOCATION)
    suspend fun getAllLocations(): Response<LocationResponse>


    @GET(ApiParams.LOCATION_ID)
    suspend fun getSingleLocation(
        @Path("id") locationId: Int
    ): Response<LocationDto>

}


