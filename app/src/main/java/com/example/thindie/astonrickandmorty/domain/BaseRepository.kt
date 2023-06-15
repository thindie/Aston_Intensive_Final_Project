package com.example.thindie.astonrickandmorty.domain

interface BaseRepository<T> {
    suspend fun getAll(url: String? = null, idS: List<String> = emptyList()): Result<List<T>>
    suspend fun getPoolOf(concretes: List<String>): Result<List<T>>
    suspend fun getConcrete(id: Int): Result<T>
     suspend fun store(things: List<T>)

}