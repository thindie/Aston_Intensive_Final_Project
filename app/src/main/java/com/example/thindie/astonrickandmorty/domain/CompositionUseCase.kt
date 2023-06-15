package com.example.thindie.astonrickandmorty.domain

import com.example.thindie.astonrickandmorty.domain.filtering.Filter

class CompositionUseCase<Domain> private constructor(private val baseRepository: BaseRepository<Domain>) {

    suspend fun fetchPoolOf(ids: List<String>): Result<List<Domain>> {
        return baseRepository.getPoolOf(ids)
    }


    suspend fun fetchAll(url: String?, idS: List<String>): Result<List<Domain>> {
        return baseRepository.getAll(url, idS)
    }


    suspend fun <F> onSpecifiedFilter(
        someFilter: Filter<Domain, F>,
        someFetch: suspend () -> Result<List<Domain>>
    )
            : Result<List<Domain>> {
        return someFetch
            .invoke()
            .mapCatching { entityList ->
                someFilter
                    .detectActualFilteringCases()
                someFilter.filter(entityList)
            }
    }

   suspend fun getConcrete(id: Int): Result<Domain> {
        return baseRepository.getConcrete(id)
    }


    companion object {
        internal fun <Domain> inject(
            baseProvider: UseCase<Domain>,
            baseRepository: BaseRepository<Domain>
        ) {
            baseProvider.useCase = CompositionUseCase(baseRepository)
            baseProvider.initialisationGuarantee()
        }
    }
}

fun String.matchCriteria(criteria: String): Boolean {
    return lowercase().trim().contains(criteria.lowercase().trim()) ||
            lowercase().trim() == criteria.lowercase().trim() ||
            criteria.lowercase().trim().contains(this.lowercase().trim())
}

