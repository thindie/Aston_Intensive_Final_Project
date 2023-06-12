package com.example.thindie.astonrickandmorty.domain

interface BaseRepository<T> {
    suspend fun getAll(url: String? = null, idS: List<String> = emptyList()): Result<List<T>>
    suspend fun getConcrete(concretes: List<String>): Result<List<T>>
     suspend fun store(things: List<T>)

}