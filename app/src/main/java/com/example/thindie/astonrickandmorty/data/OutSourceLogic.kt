package com.example.thindie.astonrickandmorty.data

open class OutSourceLogic<Domain, Remote, Local> {

    suspend fun <Response> fetchAllAsResponse(
        mapper: (Response) -> List<Domain>,
        fetcher: suspend () -> Response,
    ): Result<List<Domain>> {
        return kotlin.runCatching {
            fetcher()
        }.mapCatching(mapper)
    }

    suspend fun onFailedFetchConcrete(
        mapper: (Local) -> Domain,
        concreteReTaker: suspend () -> Local
    ): Result<Domain> {
        return kotlin
            .runCatching {
                mapper(concreteReTaker())
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

    suspend fun onFailPaginateFetchFromWeb(
        url: () -> String?,
        localFetcher: suspend (Int) -> Local,

    ): List<Local> {
        val times = FETCH_DOSE
        var startId = url()
            ?.last()
            ?.digitToInt() ?: 1
        if (startId > 1) startId = (startId - 1).times(FETCH_DOSE) + 1

        return buildList(capacity = times) {
            repeat(times) { add(startId++) }
        }.map { id ->
            localFetcher(id)
        }
    }

    suspend fun onFailedFetchGroupOfLinks(
        linksList: List<String>,
        localFetcher: suspend (Int) -> Local
    ): List<Local> {
        return linksList
            .map { link ->
                val id = link
                    .substringAfterLast("/")
                    .toInt()
                localFetcher(id)
            }
    }

    companion object {
        private const val FETCH_DOSE = 20
        fun <Domain, Remote, Local> inject(outSourced: OutSourcedImplementationLogic<Domain, Remote, Local>) {
            outSourced.setOutSource(OutSourceLogic())
        }
    }
}
