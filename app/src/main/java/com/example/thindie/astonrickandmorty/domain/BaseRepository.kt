package com.example.thindie.astonrickandmorty.domain

interface BaseRepository<T> {
    suspend fun getAll(): Result<List<T>>

    suspend fun getConcrete(id: Int): Result<T>

    suspend fun retakeAll(): List<T>

    suspend fun retakeConcrete(id: Int): T

    suspend fun store(things: List<T>)
}