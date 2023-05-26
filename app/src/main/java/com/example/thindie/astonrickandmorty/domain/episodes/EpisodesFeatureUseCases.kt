package com.example.thindie.astonrickandmorty.domain.episodes

import com.example.thindie.astonrickandmorty.domain.CompositionUseCase
import com.example.thindie.astonrickandmorty.domain.UseCase
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

    override suspend fun getPoolOf(concretes: List<String>): Result<List<EpisodeDomain>> {
        return useCase.fetchPoolOf(concretes)
    }

    override suspend fun getConcrete(id: Int): Result<EpisodeDomain> {
        return useCase.getConcrete(id)
    }

    override suspend fun getAll(
        url: String?,
    ): Result<List<EpisodeDomain>> {
        return useCase.fetchAll(url)
    }
}
