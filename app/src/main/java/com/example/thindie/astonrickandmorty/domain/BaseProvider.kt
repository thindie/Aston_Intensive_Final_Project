package com.example.thindie.astonrickandmorty.domain

import com.example.thindie.astonrickandmorty.domain.filtering.CharacterFilter

interface BaseProvider<T> {


    suspend fun getConcrete(id: Int): Result<T>

    suspend fun getAll(filter: CharacterFilter = CharacterFilter()): Result<List<T>>

    fun resetFilter()
}