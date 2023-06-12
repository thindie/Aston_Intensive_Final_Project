package com.example.thindie.astonrickandmorty.data

import com.example.thindie.astonrickandmorty.data.localsource.entity.EpisodeDbModel
import com.example.thindie.astonrickandmorty.data.localsource.entity.LocationDbModel
import com.example.thindie.astonrickandmorty.data.localsource.entity.PersonageDbModel
import com.example.thindie.astonrickandmorty.data.remotesource.entity.Info
import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesResponse
import com.example.thindie.astonrickandmorty.data.remotesource.entity.location.LocationDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.location.LocationResponse
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.Origin
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageResponse
import com.example.thindie.astonrickandmorty.domain.LinkPool
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.domain.personages.Location
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain


fun Info.toLinkPool(): LinkPool {
    return LinkPool(prev.orEmpty(), next.orEmpty())
}

val stubLinkPool: () -> LinkPool = {
    LinkPool("", "")
}

fun EpisodesDto.toEpisodesDomain(linkPoolChainer: () -> LinkPool): EpisodeDomain {
    return EpisodeDomain(
        airDate = airDate,
        characters = characters,
        created = created,
        episode = episode,
        id = id,
        name = name,
        url = url,
        pool = linkPoolChainer()
    )
}


val episodesResponseToDomain: (EpisodesResponse) -> List<EpisodeDomain> =
    { episodesResponse ->
        val info = episodesResponse.info
        episodesResponse.results.map { episodesDto ->
            episodesDto.toEpisodesDomain { info.toLinkPool() }
        }
    }

val personagesResponseToDomain: (PersonageResponse) -> List<PersonageDomain> =
    { personagesResponse ->
        val info = personagesResponse.info
        personagesResponse.results.map { personagesDto ->
            personagesDto.toPersonageDomain { info.toLinkPool() }
        }
    }

val locationResponseToDomain: (LocationResponse) -> List<LocationDomain> =
    { locationResponse ->
        val info = locationResponse.info
        locationResponse.results.map { locationsDto ->
            locationsDto.toLocationDomain { info.toLinkPool() }
        }
    }

val episodesDbModelToDomain: (EpisodeDbModel) -> EpisodeDomain = { episodeDbModel ->
    episodeDbModel.toEpisodesDomain()
}

val locationsDbModelToDomain: (LocationDbModel) -> LocationDomain = { locationsDbModel ->
    locationsDbModel.toLocationDomain()
}

val personagesDbModelToDomain: (PersonageDbModel) -> PersonageDomain = { personagesDbModel ->
    personagesDbModel.toPersonageDomain()
}


val episodesDtoToDomain: (EpisodesDto) -> EpisodeDomain = { episodeDto ->
    episodeDto.toEpisodesDomain(stubLinkPool)
}

val locationsDtoToDomain: (LocationDto) -> LocationDomain = { locationsDto ->
    locationsDto.toLocationDomain(stubLinkPool)
}

val personagesDtoToDomain: (PersonageDto) -> PersonageDomain = { personagesDto ->
    personagesDto.toPersonageDomain(stubLinkPool)
}


fun EpisodeDbModel.toEpisodesDomain(): EpisodeDomain {
    return EpisodeDomain(
        airDate = airDate,
        characters = characters,
        created = created,
        episode = episode,
        id = id,
        name = name,
        url = url,
        pool = pool
    )
}


fun PersonageDto.toPersonageDomain(linkPoolChainer: () -> LinkPool): PersonageDomain {
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
        status = status, type = type, url = url,
        pool = linkPoolChainer()
    )
}


fun LocationDto.toLocationDomain(linkPoolChainer: () -> LinkPool): LocationDomain {
    return LocationDomain(
        created = created,
        dimension = dimension,
        id = id,
        name = name,
        residents = residents,
        type = type,
        url = url,
        pool = linkPoolChainer()
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
        characters = characters,
        created = created,
        episode = episode,
        id = id,
        name = name,
        url = url,
        pool = pool
    )
}

fun LocationDbModel.toLocationDomain(): LocationDomain {
    return LocationDomain(
        created = created,
        dimension = dimension,
        id = id,
        name = name,
        residents = residents,
        type = type,
        url = url,
        pool = pool
    )
}


fun LocationDomain.toLocationDbModel(): LocationDbModel {
    return LocationDbModel(
        created = created,
        dimension = dimension,
        id = id,
        name = name,
        residents = residents,
        type = type,
        url = url,
        pool = pool
    )
}

fun PersonageDbModel.toPersonageDomain(): PersonageDomain {
    return PersonageDomain(
        id = id,
        created = created,
        episode = episode,
        gender = gender,
        image = image,
        location = location,
        name = name,
        origin = origin,
        species = species,
        status = status,
        type = type,
        url = url,
        pool = pool
    )
}

fun PersonageDomain.toPersonageDbModel(): PersonageDbModel {
    return PersonageDbModel(
        id = id,
        created = created,
        episode = episode,
        gender = gender,
        image = image,
        location = location,
        name = name,
        origin = origin,
        species = species,
        status = status,
        type = type,
        url = url,
        pool = pool
    )
}



