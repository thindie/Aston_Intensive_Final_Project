package com.example.thindie.astonrickandmorty.domain.locations

import com.example.thindie.astonrickandmorty.domain.BaseProvider

interface LocationProvider : BaseProvider<LocationDomain> {
    suspend fun getLastKnowLocation(link: String): Result<LocationDomain>
}
