package com.example.thindie.astonrickandmorty.domain.locations

data class LocationDomain(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)