package com.example.thindie.astonrickandmorty.domain

interface BaseProvider<Domain> {

    suspend fun getPoolOf(concretes: List<String>): Result<List<Domain>>

    suspend fun getConcrete(id: Int): Result<Domain>

    suspend fun getAll(
        url: String? = null,
    ): Result<List<Domain>>
}
