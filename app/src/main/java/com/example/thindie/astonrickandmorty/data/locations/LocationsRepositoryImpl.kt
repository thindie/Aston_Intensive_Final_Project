package com.example.thindie.astonrickandmorty.data.locations

import com.example.thindie.astonrickandmorty.data.assistGet
import com.example.thindie.astonrickandmorty.data.localsource.LocationsDao
import com.example.thindie.astonrickandmorty.data.remotesource.LocationApi
import com.example.thindie.astonrickandmorty.data.toLocationDomain
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.domain.locations.LocationRepository
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val locationApi: LocationApi, private val dao: LocationsDao
) : LocationRepository {
    override suspend fun getAll(): Result<List<LocationDomain>> {
        return assistGet {
            locationApi.getAllLocations()
        }.mapCatching { dto ->
            dto.results.map { locationsDto ->
                locationsDto.toLocationDomain()
            }
        }
    }


    override suspend fun getConcrete(id: Int): Result<LocationDomain> {
        return assistGet {
            locationApi.getSingleLocation(id)
        }.mapCatching { locationsDto ->
            locationsDto.toLocationDomain()
        }
    }

}