package com.example.thindie.astonrickandmorty.domain.locations

import com.example.thindie.astonrickandmorty.domain.CompositionUseCase
import com.example.thindie.astonrickandmorty.domain.UseCase
import com.example.thindie.astonrickandmorty.domain.filtering.CharacterFilter
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

    override suspend fun getAll(filter: CharacterFilter): Result<List<LocationDomain>> {
        //todo()
        return useCase.getAll()
    }

    override fun resetFilter() {

    }
}