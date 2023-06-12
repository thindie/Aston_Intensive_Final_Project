package com.example.thindie.astonrickandmorty.domain

import com.example.thindie.astonrickandmorty.domain.filtering.Filter

interface BaseProvider<Domain, Filters> {


    suspend fun getConcrete(concretes: List<String>): Result<List<Domain>>

    suspend fun getAll(
        filter: Filter<Domain, Filters>,
        url: String? = null,
        idS: List<String> = emptyList()
    ): Result<List<Domain>>



}
