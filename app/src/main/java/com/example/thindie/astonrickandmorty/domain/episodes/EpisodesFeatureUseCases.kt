package com.example.thindie.astonrickandmorty.domain.episodes

import com.example.thindie.astonrickandmorty.domain.CompositionUseCase
import com.example.thindie.astonrickandmorty.domain.UseCase
import com.example.thindie.astonrickandmorty.domain.filtering.EpisodeFilter
import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import javax.inject.Inject

class EpisodesFeatureUseCases @Inject constructor(private val baseRepository: EpisodeRepository) :
    EpisodeProvider, UseCase<EpisodeDomain> {
    override lateinit var useCase: CompositionUseCase<EpisodeDomain>
    override fun initialisationGuarantee() {
        require(this::useCase.isInitialized) { " failed UseCase<T> initialisation in ${this::class.simpleName}" }
    }

    init {
        CompositionUseCase.inject(this, baseRepository)

    }

    override suspend fun getConcrete(id: Int): Result<EpisodeDomain> {
        return useCase.fetchConcreteOrRetakeFromBase(id)
    }

    override suspend fun getAll(filter: Filter<EpisodeDomain, EpisodeFilter>): Result<List<EpisodeDomain>> {
        return if (filter.isDefault) useCase.fetchAllOrRetakeFromBase()
        else useCase.onSpecifiedFilter(filter) { useCase.fetchAllOrRetakeFromBase() }
    }

}