package com.example.thindie.astonrickandmorty.data.remotesource.entity.episode

import com.example.thindie.astonrickandmorty.data.remotesource.entity.Info

data class EpisodesResponse(
    val info: Info,
    val results: List<EpisodesDto>
)