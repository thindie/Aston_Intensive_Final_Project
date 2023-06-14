package com.example.thindie.astonrickandmorty.data

import android.util.Log
import com.example.thindie.astonrickandmorty.data.remotesource.util.commaQueryEncodedBuilder

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
        mapper: (Remote) -> Domain,
        fetcher: suspend () -> List<Remote>
    ): Result<List<Domain>> {
        return kotlin.runCatching {
            fetcher()
                .map(mapper)
        }
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

    suspend fun List<Remote>.onLocalPost(
        mapper: Remote.() -> Local,
        poster: suspend (List<Local>) -> Unit
    ) {
        poster(map(mapper))
    }

    suspend fun getConcrete(
        concretes: List<String>,
        mapper: (Remote) -> Domain,
        fetcher: suspend (Int) -> List<Remote>
    ): Result<List<Domain>> {
       /* val path = concretes
            .map { concrete ->
                concrete.last().toString()
            }
*/
        return kotlin
            .runCatching { fetcher(4) }
            .mapCatching { list -> list.map(mapper) }
    }


    companion object {
        fun <Domain, Remote, Local> inject(outSourced: OutSourcedImplementationLogic<Domain, Remote, Local>) {
            outSourced.setOutSource(OutSourceLogic())
        }
    }
}