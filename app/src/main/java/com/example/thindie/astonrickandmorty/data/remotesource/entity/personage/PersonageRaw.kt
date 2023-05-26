package com.example.thindie.astonrickandmorty.data.remotesource.entity.personage

data class PersonageRaw(
    val info: Info,
    val results: List<PersonageDto>
)