package com.example.thindie.astonrickandmorty.data.locations

import com.example.thindie.astonrickandmorty.data.OutSourceLogic
import com.example.thindie.astonrickandmorty.data.OutSourcedImplementationLogic
import com.example.thindie.astonrickandmorty.data.localsource.LocationsDao
import com.example.thindie.astonrickandmorty.data.localsource.entity.LocationDbModel
import com.example.thindie.astonrickandmorty.data.locationResponseToDomain
import com.example.thindie.astonrickandmorty.data.locationsDbModelToDomain
import com.example.thindie.astonrickandmorty.data.locationsDtoToDomain
import com.example.thindie.astonrickandmorty.data.remotesource.LocationApi
import com.example.thindie.astonrickandmorty.data.remotesource.entity.location.LocationDto
import com.example.thindie.astonrickandmorty.data.toLocationDbModel
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
        return outSourceLogic.fetchAllAsResponse(locationResponseToDomain) {
            if (url == null)
                api.getAllLocations()
            else api.getBy(url)
        }
            .onFailure {
                outSourceLogic.onFailedFetchMultiply(locationsDbModelToDomain)
                { dao.getAllLocations() }
            }
            .onSuccess { /*things -> store(things)*/ }
    }

    override suspend fun getConcrete(concretes: List<String>): Result<List<LocationDomain>> {
        return outSourceLogic.getConcrete(concretes, locationsDtoToDomain) { path ->
            api.getMultiply(path)
        }
            .onFailure {
                outSourceLogic.onFailedFetchConcrete(locationsDbModelToDomain)
                { dao.getAllLocations() }
            }
            .onSuccess { }
    }

    override suspend fun store(things: List<LocationDomain>) {
        dao.upsertLocations(things.map { personageDomain ->
            personageDomain.toLocationDbModel()
        })
    }

    override fun setOutSource(logic: OutSourceLogic<LocationDomain, LocationDto, LocationDbModel>) {
        outSourceLogic = logic
    }
}


