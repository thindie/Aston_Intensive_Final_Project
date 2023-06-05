package com.example.thindie.astonrickandmorty.domain.locations

import com.example.thindie.astonrickandmorty.domain.CompositionUseCase
import com.example.thindie.astonrickandmorty.domain.UseCase
import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import com.example.thindie.astonrickandmorty.domain.filtering.LocationsFilter
import javax.inject.Inject

class LocationsFeatureUseCase @Inject constructor(private val baseRepository: LocationRepository) :
    LocationProvider, UseCase<LocationDomain> {
    override lateinit var useCase: CompositionUseCase<LocationDomain>

    init {
        CompositionUseCase.inject(this, baseRepository)
    }

    override suspend fun getConcrete(id: Int): Result<LocationDomain> {
        return useCase.getConcrete(id)
    }

    override suspend fun getAll(filter: Filter<LocationDomain, LocationsFilter>): Result<List<LocationDomain>> {
        return if (filter.isDefault) useCase.getAll()
        else useCase.onSpecifiedFilter(filter) { useCase.getAll() }
    }

    override fun resetFilter() {

    }
}