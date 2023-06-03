package com.example.thindie.astonrickandmorty.data

import com.example.thindie.astonrickandmorty.data.remotesource.entity.location.LocationDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.Origin
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageDto
import com.example.thindie.astonrickandmorty.data.remotesource.util.ApiParams
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.domain.personages.Location
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain
import okio.IOException
import retrofit2.Response


suspend fun <T> assistGet(foo: suspend () -> Response<T>): Result<T> {
    val fooResult = foo()
    return if (fooResult.isSuccessful) {
        try {
            Result.success(requireNotNull(fooResult.body()) { ApiParams.ON_NULL_RESPONSE })
        } catch (failedRequirement: IllegalArgumentException) {
            Result.failure(failedRequirement)
        }

    } else Result.failure(IOException(ApiParams.ON_FAIL))
}

fun com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.Result.toEpisodesDomain(): EpisodeDomain {
    return EpisodeDomain(
        airDate = airDate,
        characters = characters,
        created = created,
        episode = episode,
        id = id,
        name = name,
        url = url
    )
}

fun PersonageDto.toPersonageDomain(): PersonageDomain {
    return PersonageDomain(
        id = id,
        created = created,
        episode = episode,
        gender = gender,
        image = image,
        location = location.toDomainLocation(),
        name = name,
        origin = origin.toDomainOrigin(),
        species = species,
        status = status, type = type, url = url
    )
}


fun LocationDto.toLocationDomain(): LocationDomain {
    return LocationDomain(
        created = created,
        dimension = dimension,
        id = id,
        name = name,
        residents = residents,
        type = type,
        url = url
    )
}

private fun Origin.toDomainOrigin(): com.example.thindie.astonrickandmorty.domain.personages.Origin {
    return com.example.thindie.astonrickandmorty.domain.personages.Origin(name, url)
}

private
fun com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.Location.toDomainLocation(): Location {
    return Location(name, url)
}
