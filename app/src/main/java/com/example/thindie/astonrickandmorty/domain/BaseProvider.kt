package com.example.thindie.astonrickandmorty.domain

interface BaseProvider<T>   {
    fun getAll(): Result<List<T>>

    fun getConcrete(id: Int): Result<T>
}