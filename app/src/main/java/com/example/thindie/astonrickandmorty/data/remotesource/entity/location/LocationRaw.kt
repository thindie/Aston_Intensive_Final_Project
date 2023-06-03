package com.example.thindie.astonrickandmorty.data.remotesource.entity.location

data class LocationRaw(
    val info: Info,
    val results: List<LocationDto>
)