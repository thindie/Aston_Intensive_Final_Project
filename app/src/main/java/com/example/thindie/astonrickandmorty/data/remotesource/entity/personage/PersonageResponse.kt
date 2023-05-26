package com.example.thindie.astonrickandmorty.data.remotesource.entity.personage

import com.example.thindie.astonrickandmorty.data.remotesource.entity.Info

data class PersonageResponse(
    val info: Info,
    val results: List<PersonageDto>
)