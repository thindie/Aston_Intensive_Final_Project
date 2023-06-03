package com.example.thindie.astonrickandmorty.data.remotesource.entity.location

data class LocationResponse(
    val info: Info,
    val results: List<LocationDto>
)