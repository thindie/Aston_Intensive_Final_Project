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

    override suspend fun getConcrete(concretes: List<String>): Result<List<EpisodeDomain>> {
        return useCase.fetchConcrete(concretes)
    }

    override suspend fun getAll(
        filter: Filter<EpisodeDomain, EpisodeFilter>,
        url: String?,
        idS: List<String>
    ): Result<List<EpisodeDomain>> {
        return if (filter.isDefault) useCase.fetchAll(url, idS)
        else useCase.onSpecifiedFilter(filter) { useCase.fetchAll(url, idS) }
    }

}