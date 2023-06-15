package com.example.thindie.astonrickandmorty.data.locations

import com.example.thindie.astonrickandmorty.data.OutSourceLogic
import com.example.thindie.astonrickandmorty.data.OutSourcedImplementationLogic
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
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val api: LocationApi,
    private val dao: LocationsDao
) : LocationRepository,
    OutSourcedImplementationLogic<LocationDomain, LocationDto, LocationDbModel> {

    private lateinit var outSourceLogic: OutSourceLogic<LocationDomain, LocationDto, LocationDbModel>

    init {
        OutSourceLogic.inject(this)
    }

    override suspend fun getAll(url: String?, idS: List<String>): Result<List<LocationDomain>> {
        return outSourceLogic.fetchAllAsResponse(
            mapper = locationResponseToDomain,
            fetcher = {
                if (url == null) api.getAllLocations()
                else api.getBy(url)
            }
        )
            .onFailure {
                outSourceLogic.onFailedFetchMultiply(
                    mapper = locationsDbModelToDomain,
                    multiTaker = { dao.getAllLocations() }
                )

            }
            .onSuccess { /*things -> store(things)*/ }
    }

    override suspend fun getPoolOf(concretes: List<String>): Result<List<LocationDomain>> {
        return kotlin
            .runCatching {
                val link = concretes
                    .map { link -> link.substringAfterLast("/") }
                api.getMultiLocation(commaQueryEncodedBuilder(link))
            }
            .mapCatching { list ->
                list.map(locationsDtoToDomain)
            }
            .onFailure {
                outSourceLogic.onFailedFetchMultiply(
                    mapper = locationsDbModelToDomain,
                    multiTaker = { dao.getAllLocations() }
                )
            }
    }

    override suspend fun getConcrete(id: Int): Result<LocationDomain> {
        return kotlin
            .runCatching { api.getSingleLocation(id) }
            .mapCatching (locationsDtoToDomain)
            .onFailure {
                outSourceLogic.onFailedFetchConcrete(
                    mapper = locationsDbModelToDomain,
                    concreteReTaker = { dao.getConcreteLocation(id) }
                )
            }
    }

    override suspend fun store(things: List<LocationDomain>) {
        outSourceLogic.onLocalPost(list = things,
            mapper = locationsDomainToDbModel,
            poster = { list -> dao.upsertLocations(list) })
    }

    override fun setOutSource(logic: OutSourceLogic<LocationDomain, LocationDto, LocationDbModel>) {
        outSourceLogic = logic
    }
}


