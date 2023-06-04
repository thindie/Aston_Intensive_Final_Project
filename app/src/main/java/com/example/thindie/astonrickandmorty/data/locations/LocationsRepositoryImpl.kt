package com.example.thindie.astonrickandmorty.data.locations

import com.example.thindie.astonrickandmorty.data.getAndResult
import com.example.thindie.astonrickandmorty.data.localsource.LocationsDao
import com.example.thindie.astonrickandmorty.data.remotesource.LocationApi
import com.example.thindie.astonrickandmorty.data.toLocationDbModel
import com.example.thindie.astonrickandmorty.data.toLocationDomain
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.domain.locations.LocationRepository
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val api: LocationApi, private val dao: LocationsDao
) : LocationRepository {
    override suspend fun getAll(): Result<List<LocationDomain>> {
        return getAndResult {
            api.getAllLocations()
        }.mapCatching { rawDto ->
            rawDto.results.map { locationsDto ->
                locationsDto.toLocationDomain()
            }
        }
    }


    override suspend fun getConcrete(id: Int): Result<LocationDomain> {
        return getAndResult {
            api.getSingleLocation(id)
        }.mapCatching { locationsDto ->
            locationsDto.toLocationDomain()
        }
    }

    override suspend fun retakeAll(): List<LocationDomain> {
        return dao.getAllLocations()
            .map { locationDbModel ->
                locationDbModel
                    .toLocationDomain()
            }
    }

    override suspend fun retakeConcrete(id: Int): LocationDomain {
        return dao.getConcreteLocation(id).toLocationDomain()
    }

    override suspend fun store(things: List<LocationDomain>) {
        dao.upsertLocations(things.map { locationDomain ->
            locationDomain.toLocationDbModel()
        })
    }

}


