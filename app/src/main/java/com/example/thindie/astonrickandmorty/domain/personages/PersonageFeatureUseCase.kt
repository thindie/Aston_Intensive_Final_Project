package com.example.thindie.astonrickandmorty.domain.personages

import com.example.thindie.astonrickandmorty.domain.CompositionUseCase
import com.example.thindie.astonrickandmorty.domain.UseCase
import com.example.thindie.astonrickandmorty.domain.filtering.CharacterFilter
import javax.inject.Inject

class PersonageFeatureUseCase @Inject constructor(private val baseRepository: PersonageRepository) :
    PersonageProvider, UseCase<PersonageDomain> {

    override lateinit var useCase: CompositionUseCase<PersonageDomain>

    init {
        CompositionUseCase.inject(this, baseRepository)
    }


    override suspend fun getConcrete(id: Int): Result<PersonageDomain> {
        return useCase.getConcrete(id)
    }

    override suspend fun getAll(filter: CharacterFilter): Result<List<PersonageDomain>> {
        //todo filtering
        return useCase.getAll()
    }

    override fun resetFilter() {

    }

/*
    private suspend fun onSpecifiedFilter(foo: suspend () -> Result<List<PersonageDomain>>)
            : Result<List<PersonageDomain>> {
        val resultList: MutableList<PersonageDomain> = mutableListOf()
        return foo
            .invoke()
            .mapCatching { personageDomainList ->
                resultList += personageDomainList
                currentFilter
                    .getActualFilteringCases()
                    .forEach { filter ->
                        when (filter) {
                            1 -> {
                                resultList.map { it.name.matchCriteria(currentFilter.name) }
                            }
                            2 -> {
                                resultList.map { it.status.matchCriteria(currentFilter.status.toString()) }
                            }
                            3 -> {
                                resultList.map { it.species.matchCriteria(currentFilter.species) }
                            }
                            4 -> {
                                resultList.map { it.type.matchCriteria(currentFilter.type) }
                            }
                            5 -> {
                                resultList.map { it.gender.matchCriteria(currentFilter.gender.toString()) }
                            }
                        }
                    }
                resultList.toList()
            }
    }
*/

    private suspend fun onTimeStamp() {

    }


    private fun String.matchCriteria(criteria: String): Boolean {
        return lowercase().contains(criteria.lowercase()) ||
                lowercase() == criteria.lowercase() ||
                criteria.lowercase().contains(this.lowercase())
    }

    companion object {
        private var timeStamp: Long = 0
    }
}