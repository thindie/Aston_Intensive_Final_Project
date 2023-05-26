package com.example.thindie.astonrickandmorty.data.personages

import com.example.thindie.astonrickandmorty.data.OutSourceLogic
import com.example.thindie.astonrickandmorty.data.OutSourcedImplementationLogic
import com.example.thindie.astonrickandmorty.data.di.DispatchersIOModule
import com.example.thindie.astonrickandmorty.data.localsource.PersonagesDao
import com.example.thindie.astonrickandmorty.data.localsource.entity.PersonageDbModel
import com.example.thindie.astonrickandmorty.data.personagesDbModelToDomain
import com.example.thindie.astonrickandmorty.data.personagesDomainToDbModel
import com.example.thindie.astonrickandmorty.data.personagesDtoToDomain
import com.example.thindie.astonrickandmorty.data.personagesResponseToDomain
import com.example.thindie.astonrickandmorty.data.remotesource.PersonagesApi
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageDto
import com.example.thindie.astonrickandmorty.data.remotesource.util.commaQueryEncodedBuilder
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain
import com.example.thindie.astonrickandmorty.domain.personages.PersonageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PersonagesRepositoryImpl @Inject constructor(
    private val api: PersonagesApi,
    private val dao: PersonagesDao,
    @DispatchersIOModule.IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : PersonageRepository,
    OutSourcedImplementationLogic<PersonageDomain, PersonageDto, PersonageDbModel> {
    private lateinit var outSourceLogic: OutSourceLogic<PersonageDomain, PersonageDto, PersonageDbModel>

    private var isActualiseNeeded = true
    private var isNetworkActive = false

    init {
        OutSourceLogic.inject(this)
    }

    override suspend fun getAll(url: String?): Result<List<PersonageDomain>> {
        return outSourceLogic.fetchAllAsResponse(
            mapper = personagesResponseToDomain,
            fetcher = {
                if (url == null) {
                    api.getAllCharacters()
                } else api.getBy(url)
            }
        )
            .onFailure {
                withContext(ioDispatcher) {
                    outSourceLogic.onFailedFetchMultiply(
                        mapper = personagesDbModelToDomain,
                        multiTaker = {
                            outSourceLogic.onFailPaginateFetchFromWeb(
                                url = { url },
                                localFetcher = { id -> dao.getConcretePersonage(id) },
                            )
                        }
                    )
                }
                    .onFailure {
                        return Result.failure(it)
                    }
                    .onSuccess {
                        return Result.success(it)
                    }
            }
            .onSuccess { things ->
                withContext(ioDispatcher) {
                    store(things)
                }
            }
    }

    override suspend fun getPoolOf(concretes: List<String>): Result<List<PersonageDomain>> {
        return kotlin
            .runCatching {
                val link = concretes
                    .map { link -> link.substringAfterLast("/") }
                api.getMultiCharacter(commaQueryEncodedBuilder(link))
            }
            .mapCatching { list ->
                list.map(personagesDtoToDomain)
            }
            .onFailure {
                outSourceLogic.onFailedFetchMultiply(
                    mapper = personagesDbModelToDomain,
                    multiTaker = {
                        outSourceLogic.onFailedFetchGroupOfLinks(
                            linksList = concretes,
                            localFetcher = { id -> dao.getConcretePersonage(id) }
                        )
                    }
                )
                    .onFailure {
                        return Result.failure(it)
                    }
                    .onSuccess {
                        return Result.success(it)
                    }
            }
            .onSuccess { things ->
                withContext(ioDispatcher) {
                    store(things)
                }
            }
    }

    override suspend fun getConcrete(id: Int): Result<PersonageDomain> {
        return kotlin
            .runCatching { api.getSingleCharacter(id) }
            .mapCatching(personagesDtoToDomain)
            .onFailure {
                outSourceLogic.onFailedFetchConcrete(
                    mapper = personagesDbModelToDomain,
                    concreteReTaker = { dao.getConcretePersonage(id) }
                )
                    .onFailure {
                        return Result.failure(it)
                    }
                    .onSuccess {
                        return Result.success(it)
                    }
            }
            .onSuccess { thing ->
                store(listOf(thing))
            }
    }

    override suspend fun store(things: List<PersonageDomain>) {
        outSourceLogic.onLocalPost(
            list = things,
            mapper = personagesDomainToDbModel,
            poster = { list -> dao.upsertPersonages(list) }
        )
    }

    override fun setOutSource(logic: OutSourceLogic<PersonageDomain, PersonageDto, PersonageDbModel>) {
        outSourceLogic = logic
    }
}
