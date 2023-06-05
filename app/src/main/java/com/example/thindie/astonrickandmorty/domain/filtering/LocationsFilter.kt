package com.example.thindie.astonrickandmorty.domain.filtering

import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.domain.matchCriteria

class LocationsFilter(
    val name: String = DEFAULT_VALUE,
    val type: String = DEFAULT_VALUE,
    val dimension: String = DEFAULT_VALUE,
) : Filter<LocationDomain, LocationsFilter> {
    override val isDefault: Boolean
        get() = isDefaults()


    private fun isDefaults() =
        name == defaultValue &&
                type == defaultValue &&
                dimension == defaultValue

    override fun detectActualFilteringCases(): LocationsFilter {
        diffList.clear()
        if (name != defaultValue) diffList += 1
        if (type != defaultValue) diffList += 2
        if (dimension != defaultValue) diffList += 3
        return this
    }

    override fun filter(list: List<LocationDomain>): List<LocationDomain> {
        val resultList = list.toMutableList()
        diffList.forEach { filter ->
            when (filter) {
                1 -> {
                    resultList.map { it.name.matchCriteria(name) }
                }
                2 -> {
                    resultList.map { it.type.matchCriteria(type) }
                }
                3 -> {
                    resultList.map { it.dimension.matchCriteria(dimension) }
                }
            }
        }
        if (resultList.isEmpty()) error(unSuccessFiltering)
        return resultList
    }

    companion object {
        private val diffList: MutableList<Int> = mutableListOf()

        private const val DEFAULT_VALUE = ""

    }
}

