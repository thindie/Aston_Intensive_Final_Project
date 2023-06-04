package com.example.thindie.astonrickandmorty.data

import com.example.thindie.astonrickandmorty.data.localsource.entity.EpisodeDbModel
import com.example.thindie.astonrickandmorty.data.localsource.entity.LocationDbModel
import com.example.thindie.astonrickandmorty.data.localsource.entity.PersonageDbModel
import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesDto
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


suspend fun <T> getAndResult(foo: suspend () -> Response<T>): Result<T> {
    val fooResult = foo()
    return if (fooResult.isSuccessful) {
        try {
            Result.success(requireNotNull(fooResult.body()) { ApiParams.ON_NULL_RESPONSE })
        } catch (failedRequirement: IllegalArgumentException) {
            Result.failure(failedRequirement)
        }

    } else Result.failure(IOException(ApiParams.ON_FAIL))
}

fun EpisodesDto.toEpisodesDomain(): EpisodeDomain {
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

fun EpisodeDbModel.toEpisodesDomain(): EpisodeDomain {
    return EpisodeDomain(
        airDate = airDate,
        characters = Converter.decompress(charactersList),
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


fun EpisodeDomain.toEpisodeDbModel(): EpisodeDbModel {
    return EpisodeDbModel(
        airDate = airDate,
        charactersList = Converter.compress(characters),
        created = created,
        episode = episode,
        id = id,
        name = name,
        url = url
    )
}

fun LocationDbModel.toLocationDomain(): LocationDomain {
    return LocationDomain(
        created = created,
        dimension = dimension,
        id = id,
        name = name,
        residents = Converter.decompress(residentsList),
        type = type,
        url = url
    )
}


fun LocationDomain.toLocationDbModel(): LocationDbModel {
    return LocationDbModel(
        created = created,
        dimension = dimension,
        id = id,
        name = name,
        residentsList = Converter.compress(residents),
        type = type,
        url = url
    )
}

fun PersonageDbModel.toPersonageDomain(): PersonageDomain {
    return PersonageDomain(
        id = id,
        created = created,
        episode = Converter.decompress(episodesList),
        gender = gender,
        image = image,
        location = Converter.decompressLocation(location),
        name = name,
        origin = Converter.decompressOrigin(origin),
        species = species,
        status = status, type = type, url = url
    )
}

fun PersonageDomain.toPersonageDbModel(): PersonageDbModel {
    return PersonageDbModel(
        id = id,
        created = created,
        episodesList = Converter.compress(episode),
        gender = gender,
        image = image,
        location = Converter.compress(location),
        name = name,
        origin = Converter.compress(origin),
        species = species,
        status = status, type = type, url = url
    )
}

class Converter private constructor() {
    companion object {
        private const val SEPARATOR = "~-l1zzztSeqyaparatyort1!!!11-~"
        fun <T> compress(list: Iterable<T>): String {
            return list.joinToString { SEPARATOR }
        }

        fun decompress(string: String): List<String> {
            return string.split(SEPARATOR)
        }

        fun compress(location: Location): String {
            return location.name.plus(SEPARATOR).plus(location.url)
        }

        fun compress(origin: com.example.thindie.astonrickandmorty.domain.personages.Origin): String {
            return origin.name.plus(SEPARATOR).plus(origin.url)
        }

        fun decompressOrigin(string: String): com.example.thindie.astonrickandmorty.domain.personages.Origin {
            return try {
                val list = decompress(string)
                com.example.thindie.astonrickandmorty.domain.personages.Origin(
                    list.first(),
                    list.last()
                )
            } catch (e: Exception) {
                com.example.thindie.astonrickandmorty.domain.personages.Origin("", "")
            }
        }

        fun decompressLocation(string: String): Location {
            return try {
                val list = decompress(string)
                Location(list.first(), list.last())
            } catch (e: Exception) {
                Location("", "")
            }
        }
    }
}


