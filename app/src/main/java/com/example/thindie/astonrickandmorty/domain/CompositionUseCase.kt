package com.example.thindie.astonrickandmorty.domain

import com.example.thindie.astonrickandmorty.domain.filtering.Filter

class CompositionUseCase<Domain> private constructor(private val baseRepository: BaseRepository<Domain>) {

    suspend fun getConcrete(id: Int): Result<Domain> {
        var result: Result<Domain>? = null
        baseRepository
            .getConcrete(id)
            .onFailure {
                result = reTake(id)
            }.onSuccess { gotSuccessEntity ->
                result = Result.success(gotSuccessEntity)
            }
        return requireNotNull(result) { "See Personage Feature Use case" }
    }


    suspend fun getAll(): Result<List<Domain>> {
        val list = mutableListOf<Domain>()
        return baseRepository
            .getAll()
            .onSuccess { list.addAll(it); Result.success(list) }
            .onFailure { throwable -> /* this or onReTake? */
                try {
                    reTakeAll()
                        .onSuccess { list.addAll(it) }
                    Result.success(list)
                } catch (onReTake: Exception) {
                    Result.failure<Domain>(onReTake)
                }
            }
    }


    private suspend fun reTake(id: Int): Result<Domain> {
        return try {
            val reTaken = baseRepository.retakeConcrete(id)
            requireNotNull(reTaken)
            Result.success(reTaken)
        } catch (nullReTake: Exception) {
            Result.failure(nullReTake)
        }
    }

    private suspend fun reTakeAll(): Result<List<Domain>> {
        val result = baseRepository.retakeAll()
        if (result.isEmpty()) error { "looks like last chance re-take some data is failed" }
        return Result.success(result)
    }

    suspend fun <F> onSpecifiedFilter(
        someFilter: Filter<Domain, F>,
        foo: suspend () -> Result<List<Domain>>
    )
            : Result<List<Domain>> {
        return foo
            .invoke()
            .mapCatching { entityList ->
                someFilter
                    .detectActualFilteringCases()
                someFilter.filter(entityList)
            }
    }


    companion object {
      internal  fun <Domain> inject(
            baseProvider: UseCase<Domain>,
            baseRepository: BaseRepository<Domain>
        ) {
            baseProvider.useCase = CompositionUseCase(baseRepository)
        }
    }
}

fun String.matchCriteria(criteria: String): Boolean {
    return lowercase().trim().contains(criteria.lowercase().trim()) ||
            lowercase().trim() == criteria.lowercase().trim() ||
            criteria.lowercase().trim().contains(this.lowercase().trim())
}