package com.example.thindie.astonrickandmorty.data.remotesource.entity.personage

data class PersonageResponse(
    val info: Info,
    val results: List<PersonageDto>
)