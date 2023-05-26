package com.example.thindie.astonrickandmorty.domain

class CompositionUseCase<Domain> private constructor(private val baseRepository: BaseRepository<Domain>) {

    suspend fun fetchPoolOf(ids: List<String>): Result<List<Domain>> {
        return baseRepository.getPoolOf(ids)
    }

    suspend fun fetchAll(url: String?): Result<List<Domain>> {
        return baseRepository.getAll(url)
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
