package com.example.thindie.astonrickandmorty.domain

class CompositionUseCase<T>(private val baseRepository: BaseRepository<T>) {

    suspend fun getConcrete(id: Int): Result<T> {
        var result: Result<T>? = null
        baseRepository
            .getConcrete(id)
            .onFailure {
                result = reTake(id)
            }.onSuccess { gotSuccessEntity ->
                result = Result.success(gotSuccessEntity)
            }
        return requireNotNull(result) { "See Personage Feature Use case" }
    }


    suspend fun getAll(): Result<List<T>> {
        val list = mutableListOf<T>()
        return baseRepository
            .getAll()
            .onSuccess { list.addAll(it); Result.success(list) }
            .onFailure { throwable -> /* this or onReTake? */
                try {
                    reTakeAll()
                        .onSuccess { list.addAll(it) }
                    Result.success(list)
                } catch (onReTake: Exception) {
                    Result.failure<T>(onReTake)
                }
            }
    }


    private suspend fun reTake(id: Int): Result<T> {
        return try {
            val reTaken = baseRepository.retakeConcrete(id)
            requireNotNull(reTaken)
            Result.success(reTaken)
        } catch (nullReTake: Exception) {
            Result.failure(nullReTake)
        }
    }

    private suspend fun reTakeAll(): Result<List<T>> {
        val result = baseRepository.retakeAll()
        if (result.isEmpty()) error() { "looks last chance re-take some data is failed" }
        return Result.success(result)
    }

    companion object {
        fun <T> inject(
            baseProvider: BaseProvider<T>,
            baseRepository: BaseRepository<T>
        ) {
            baseProvider.useCase = CompositionUseCase(baseRepository)
        }
    }
}