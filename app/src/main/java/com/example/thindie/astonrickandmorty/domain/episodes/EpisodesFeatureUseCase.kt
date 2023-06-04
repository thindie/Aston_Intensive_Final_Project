package com.example.thindie.astonrickandmorty.domain.episodes

import com.example.thindie.astonrickandmorty.domain.CompositionUseCase
import com.example.thindie.astonrickandmorty.domain.UseCase
import com.example.thindie.astonrickandmorty.domain.filtering.CharacterFilter
import javax.inject.Inject

class EpisodesFeatureUseCase @Inject constructor(private val baseRepository: EpisodeRepository) :
    EpisodeProvider, UseCase<EpisodeDomain> {
    override lateinit var useCase: CompositionUseCase<EpisodeDomain>

    init {
        CompositionUseCase.inject(this, baseRepository)
    }

    override suspend fun getConcrete(id: Int): Result<EpisodeDomain> {
        return useCase.getConcrete(id)
    }

    override suspend fun getAll(filter: CharacterFilter): Result<List<EpisodeDomain>> {
        //todo)
        return useCase.getAll()
    }

    override fun resetFilter() {

    }
}