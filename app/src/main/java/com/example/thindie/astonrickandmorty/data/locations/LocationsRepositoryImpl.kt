package com.example.thindie.astonrickandmorty.data.locations

import com.example.thindie.astonrickandmorty.data.OutSourceLogic
import com.example.thindie.astonrickandmorty.data.OutSourcedImplementationLogic
import com.example.thindie.astonrickandmorty.data.di.DispatchersIOModule
import com.example.thindie.astonrickandmorty.data.localsource.LocationsDao
import com.example.thindie.astonrickandmorty.data.localsource.entity.LocationDbModel
import com.example.thindie.astonrickandmorty.data.locationResponseToDomain
import com.example.thindie.astonrickandmorty.data.locationsDbModelToDomain
import com.example.thindie.astonrickandmorty.data.locationsDomainToDbModel
import com.example.thindie.astonrickandmorty.data.locationsDtoToDomain
import com.example.thindie.astonrickandmorty.data.remotesource.LocationApi
import com.example.thindie.astonrickandmorty.data.remotesource.entity.location.LocationDto
import com.example.thindie.astonrickandmorty.data.remotesource.util.commaQueryEncodedBuilder
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.domain.locations.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val api: LocationApi,
    private val dao: LocationsDao,
    @DispatchersIOModule.IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : LocationRepository,
    OutSourcedImplementationLogic<LocationDomain, LocationDto, LocationDbModel> {

    private lateinit var outSourceLogic: OutSourceLogic<LocationDomain, LocationDto, LocationDbModel>

    private var isActualiseNeeded = true
    private var isNetworkActive = false

    init {
        OutSourceLogic.inject(this)
    }

    override suspend fun getSingleByUrl(link: String): Result<LocationDomain> {
        getPoolOf(listOf(link))
            .onSuccess { list ->
                store(list)
                return Result.success(list.first())
            }
            .onFailure { error ->
                return Result.failure(error)
            }
        return Result.failure(requireNotNull(null)) // due kotlin bug
    }

    override suspend fun getAll(url: String?): Result<List<LocationDomain>> {
        return outSourceLogic.fetchAllAsResponse(
            mapper = locationResponseToDomain,
            fetcher = {
                if (url == null) {
                    api.getAllLocations()
                } else api.getBy(url)
            }
        )
            .onFailure {
                outSourceLogic.onFailedFetchMultiply(
                    mapper = locationsDbModelToDomain,
                    multiTaker = {
                        outSourceLogic.onFailPaginateFetchFromWeb(
                            url = { url },
                            localFetcher = { id -> dao.getConcreteLocation(id) },
                        )
                    }
                ).onFailure {
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

    override suspend fun getPoolOf(concretes: List<String>): Result<List<LocationDomain>> {
        return kotlin
            .runCatching {
                val link = concretes
                    .map { link -> link.substringAfterLast("/") }
                    .filterNot { it.isBlank() }
                api.getMultiLocation(commaQueryEncodedBuilder(link))
            }
            .mapCatching { list ->
                list
                    .filterNot { it.id == 0 }
                    .map(locationsDtoToDomain)
            }
            .onFailure {
                outSourceLogic.onFailedFetchMultiply(
                    mapper = locationsDbModelToDomain,
                    multiTaker = {
                        outSourceLogic.onFailedFetchGroupOfLinks(
                            linksList = concretes,
                            localFetcher = { id -> dao.getConcreteLocation(id) }
                        )
                    }
                )
                    .onFailure {
                        return Result.failure(it)
                    }
                    .onSuccess {
                        return Result.success(it)
                    }
            }.onSuccess { things ->
                withContext(ioDispatcher) {
                    store(things)
                }
            }
    }

    override suspend fun getConcrete(id: Int): Result<LocationDomain> {
        return kotlin
            .runCatching { api.getSingleLocation(id) }
            .mapCatching(locationsDtoToDomain)
            .onFailure {
                outSourceLogic.onFailedFetchConcrete(
                    mapper = locationsDbModelToDomain,
                    concreteReTaker = { dao.getConcreteLocation(id) }
                )
                    .onFailure {
                        return Result.failure(it)
                    }
                    .onSuccess {
                        return Result.success(it)
                    }
            }.onSuccess { thing ->
                withContext(ioDispatcher) {
                    store(listOf(thing))
                }
            }
    }

    override suspend fun store(things: List<LocationDomain>) {
        outSourceLogic.onLocalPost(
            list = things,
            mapper = locationsDomainToDbModel,
            poster = { list -> dao.upsertLocations(list) }
        )
    }

    override fun setOutSource(logic: OutSourceLogic<LocationDomain, LocationDto, LocationDbModel>) {
        outSourceLogic = logic
    }
}
