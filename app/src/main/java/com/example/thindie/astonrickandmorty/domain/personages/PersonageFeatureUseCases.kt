package com.example.thindie.astonrickandmorty.domain.personages

import com.example.thindie.astonrickandmorty.domain.CompositionUseCase
import com.example.thindie.astonrickandmorty.domain.UseCase
import com.example.thindie.astonrickandmorty.domain.filtering.CharacterFilter
import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import javax.inject.Inject

class PersonageFeatureUseCases @Inject constructor(private val baseRepository: PersonageRepository) :
    PersonageProvider, UseCase<PersonageDomain> {

    override lateinit var useCase: CompositionUseCase<PersonageDomain>

    init {
        CompositionUseCase.inject(this, baseRepository)
    }


    override suspend fun getConcrete(id: Int): Result<PersonageDomain> {
        return useCase.getConcrete(id)
    }

    override suspend fun getAll(filter: Filter<PersonageDomain, CharacterFilter>): Result<List<PersonageDomain>> {
        return if (filter.isDefault) useCase.getAll()
        else useCase.onSpecifiedFilter(filter) { useCase.getAll() }
    }

    override fun resetFilter() {

    }

}