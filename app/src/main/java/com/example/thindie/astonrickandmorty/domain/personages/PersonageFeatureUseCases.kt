package com.example.thindie.astonrickandmorty.domain.personages

import android.util.Log
import com.example.thindie.astonrickandmorty.domain.CompositionUseCase
import com.example.thindie.astonrickandmorty.domain.UseCase
import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import com.example.thindie.astonrickandmorty.domain.filtering.PersonageFilter
import javax.inject.Inject

class PersonageFeatureUseCases @Inject constructor(private val baseRepository: PersonageRepository) :
    PersonageProvider, UseCase<PersonageDomain> {

    override lateinit var useCase: CompositionUseCase<PersonageDomain>

    init {
        CompositionUseCase.inject(this, baseRepository)
    }

    override fun initialisationGuarantee() {
        require(this::useCase.isInitialized) { " failed UseCase<T> initialisation in ${this::class.simpleName}" }
    }


    override suspend fun getPoolOf(concretes: List<String>): Result<List<PersonageDomain>> {
        return useCase.fetchPoolOf(concretes)
    }

    override suspend fun getConcrete(id: Int): Result<PersonageDomain> {
        return useCase.getConcrete(id)
    }

    override suspend fun getAll(
        filter: Filter<PersonageDomain, PersonageFilter>,
        url: String?,
        idS: List<String>
    ): Result<List<PersonageDomain>> {

        return if (filter.isDefault) useCase.fetchAll(url, idS)
        else useCase.onSpecifiedFilter(filter) { useCase.fetchAll(url, idS) }
    }


}