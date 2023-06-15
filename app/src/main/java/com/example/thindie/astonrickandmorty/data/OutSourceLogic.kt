package com.example.thindie.astonrickandmorty.data

class OutSourceLogic<Domain, Remote, Local> {


    suspend fun <Response> fetchAllAsResponse(
        mapper: (Response) -> List<Domain>,
        fetcher: suspend () -> Response,
    ): Result<List<Domain>> {
        return kotlin.runCatching {
            fetcher()
        }.mapCatching(mapper)
    }

    suspend fun fetchAllAsDto(
        list: List<String>,
        mapper: (Remote) -> Domain,
        fetcher: suspend (String) -> Remote
    ): Result<List<Domain>> {
        return kotlin
            .runCatching {
                list.map { param -> fetcher(param) }
            }.mapCatching { remote -> remote.map(mapper) }
    }


    suspend fun onFailedFetchConcrete(
        mapper: Local.() -> Domain, concreteReTaker: suspend () -> List<Local>
    ): Result<List<Domain>> {
        return kotlin
            .runCatching {
                concreteReTaker()
                    .map(mapper)
            }
    }

    suspend fun onFailedFetchMultiply(
        mapper: (Local) -> Domain,
        multiTaker: suspend () -> List<Local>
    ): Result<List<Domain>> {
        return kotlin
            .runCatching { multiTaker() }
            .mapCatching { localList ->
                localList.map(mapper)
            }
    }

    suspend fun onLocalPost(
        list: List<Domain>,
        mapper: Domain.() -> Local,
        poster: suspend (List<Local>) -> Unit
    ) {
        poster(list.map(mapper))
    }

    suspend fun getConcrete(
        mapper: (Remote) -> Domain,
        fetcher: suspend () -> Remote
    ): Result<Domain> {
        return kotlin
            .runCatching { mapper(fetcher()) }
    }


    companion object {
        fun <Domain, Remote, Local> inject(outSourced: OutSourcedImplementationLogic<Domain, Remote, Local>) {
            outSourced.setOutSource(OutSourceLogic())
        }
    }
}