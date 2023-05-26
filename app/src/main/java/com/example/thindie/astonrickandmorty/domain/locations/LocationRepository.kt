package com.example.thindie.astonrickandmorty.domain.locations

import com.example.thindie.astonrickandmorty.domain.BaseRepository

interface LocationRepository : BaseRepository<LocationDomain> {
    suspend fun getSingleByUrl(link: String): Result<LocationDomain>
}