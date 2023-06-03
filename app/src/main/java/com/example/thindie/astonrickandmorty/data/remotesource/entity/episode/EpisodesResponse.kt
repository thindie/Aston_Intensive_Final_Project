package com.example.thindie.astonrickandmorty.data.remotesource.entity.episode

data class EpisodesResponse(
    val info: Info,
    val results: List<EpisodesDto>
)