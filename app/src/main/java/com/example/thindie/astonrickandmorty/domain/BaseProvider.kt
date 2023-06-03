package com.example.thindie.astonrickandmorty.domain

import com.example.thindie.astonrickandmorty.domain.filtering.Filter

interface BaseProvider<Domain, Filters> {


    suspend fun getConcrete(id: Int): Result<Domain>

    suspend fun getAll(filter: Filter<Domain, Filters>): Result<List<Domain>>

}