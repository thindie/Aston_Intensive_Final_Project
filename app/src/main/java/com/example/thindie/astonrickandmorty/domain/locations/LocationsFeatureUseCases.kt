package com.example.thindie.astonrickandmorty.domain.locations

import com.example.thindie.astonrickandmorty.domain.CompositionUseCase
import com.example.thindie.astonrickandmorty.domain.UseCase
import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import com.example.thindie.astonrickandmorty.domain.filtering.LocationsFilter
import javax.inject.Inject

class LocationsFeatureUseCases @Inject constructor(private val baseRepository: LocationRepository) :
    LocationProvider, UseCase<LocationDomain> {
    override lateinit var useCase: CompositionUseCase<LocationDomain>

    init {
        CompositionUseCase.inject(this, baseRepository)
    }

    override fun initialisationGuarantee() {
        require(this::useCase.isInitialized) { " failed UseCase<T> initialisation in ${this::class.simpleName}" }
    }

    override suspend fun getPoolOf(concretes: List<String>): Result<List<LocationDomain>> {
        return useCase.fetchPoolOf(concretes)
    }

    override suspend fun getConcrete(id: Int): Result<LocationDomain> {
        return useCase.getConcrete(id)
    }

    override suspend fun getAll(
        filter: Filter<LocationDomain, LocationsFilter>,
        url: String?,
        idS: List<String>
    ): Result<List<LocationDomain>> {
        return if (filter.isDefault) useCase.fetchAll(url, idS)
        else useCase.onSpecifiedFilter(filter) { useCase.fetchAll(url, idS) }
    }

}