package com.example.thindie.astonrickandmorty.domain.locations

import com.example.thindie.astonrickandmorty.domain.CompositionUseCase
import com.example.thindie.astonrickandmorty.domain.UseCase
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

    override suspend fun getLastKnowLocation(link: String): Result<LocationDomain> {
        return baseRepository.getSingleByUrl(link)
    }

    override suspend fun getPoolOf(concretes: List<String>): Result<List<LocationDomain>> {
        return useCase.fetchPoolOf(concretes)
    }

    override suspend fun getConcrete(id: Int): Result<LocationDomain> {
        return useCase.getConcrete(id)
    }

    override suspend fun getAll(url: String?): Result<List<LocationDomain>> {
        return useCase.fetchAll(url)
    }
}
