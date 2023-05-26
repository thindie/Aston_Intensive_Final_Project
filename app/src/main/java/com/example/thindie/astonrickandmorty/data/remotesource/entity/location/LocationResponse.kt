package com.example.thindie.astonrickandmorty.data.remotesource.entity.location

import com.example.thindie.astonrickandmorty.data.remotesource.entity.Info

data class LocationResponse(
    val info: Info,
    val results: List<LocationDto>
)
