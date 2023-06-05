package com.example.thindie.astonrickandmorty.domain.filtering

interface Filter<Entity, Filter> {

    val unSuccessFiltering: String
        get() = UN_SUCCESS
    val defaultValue: String
        get() = DEFAULT_VALUE

    val isDefault: Boolean

    fun detectActualFilteringCases(): Filter
    fun filter(list: List<Entity>): List<Entity>

}

private const val UN_SUCCESS = " there is nothing to show by applied filter "
private const val DEFAULT_VALUE = ""