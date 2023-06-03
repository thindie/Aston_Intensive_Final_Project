package com.example.thindie.astonrickandmorty.domain

interface BaseRepository<T> {
    suspend fun getAll(): Result<List<T>>

    suspend fun getConcrete(id: Int): Result<T>
    // suspend fun filter(filter: CharacterFilter): Result<List<T>>
}