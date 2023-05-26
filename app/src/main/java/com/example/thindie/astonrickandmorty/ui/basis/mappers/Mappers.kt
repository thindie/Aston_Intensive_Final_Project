package com.example.thindie.astonrickandmorty.ui.basis.mappers

import com.example.thindie.astonrickandmorty.domain.LinkPool
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.domain.personages.Location
import com.example.thindie.astonrickandmorty.domain.personages.Origin
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.UiLinkPool
import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesUiModel
import com.example.thindie.astonrickandmorty.ui.locations.LocationsUiModel
import com.example.thindie.astonrickandmorty.ui.personage.LocationUi
import com.example.thindie.astonrickandmorty.ui.personage.OriginUi
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesUiModel

fun EpisodeDomain.toEpisodesUiModel(): EpisodesUiModel {
    return EpisodesUiModel(
        airDate = airDate,
        characters = characters,
        created = created,
        episode = episode,
        id = id,
        name = name,
        url = url,
        pool = pool.toUiLinkPool()
    )
}

val mapEpisodesDomainToUiModel: (EpisodeDomain) -> EpisodesUiModel = {
    it.toEpisodesUiModel()
}

fun EpisodesUiModel.toEpisodesDomain(): EpisodeDomain {
    return EpisodeDomain(
        airDate = airDate,
        characters = characters,
        created = created,
        episode = episode,
        id = id,
        name = name,
        url = url,
        pool = pool.toLinkPool()
    )
}

fun LocationDomain.toLocationUiModel(): LocationsUiModel {
    return LocationsUiModel(
        created = created,
        dimension = dimension,
        id = id,
        name = name,
        residents = residents,
        type = type,
        url = url,
        pool = pool.toUiLinkPool()
    )
}

val mapLocationDomainToUiModel: (LocationDomain) -> LocationsUiModel = {
    it.toLocationUiModel()
}

fun LocationsUiModel.toLocationDomain(): LocationDomain {
    return LocationDomain(
        created = created,
        dimension = dimension,
        id = id,
        name = name,
        residents = residents,
        type = type,
        url = url,
        pool = pool.toLinkPool()
    )
}

fun PersonageDomain.toPersonagesUiModel(): PersonagesUiModel {
    return PersonagesUiModel(
        id = id,
        created = created,
        episode = episode,
        gender = gender,
        image = image,
        location = location.toLocationUi(),
        name = name,
        origin = origin.toOriginUi(),
        species = species,
        status = status,
        type = type,
        url = url,
        pool = pool.toUiLinkPool()
    )
}

val mapPersonageDomainToUiModel: (PersonageDomain) -> PersonagesUiModel = {
    it.toPersonagesUiModel()
}

fun PersonagesUiModel.toPersonagesDomain(): PersonageDomain {
    return PersonageDomain(
        id = id,
        created = created,
        episode = episode,
        gender = gender,
        image = image,
        location = location.toLocation(),
        name = name,
        origin = origin.toOrigin(),
        species = species,
        status = status,
        type = type,
        url = url,
        pool = pool.toLinkPool()
    )
}

private fun Origin.toOriginUi(): OriginUi {
    return OriginUi(name, url)
}

fun Location.toLocationUi(): LocationUi {
    return LocationUi(name, url)
}

private fun OriginUi.toOrigin(): Origin {
    return Origin(name, url)
}

fun LocationUi.toLocation(): Location {
    return Location(name, url)
}

fun UiLinkPool.toLinkPool(): LinkPool {
    return LinkPool(prev, next)
}

fun LinkPool.toUiLinkPool(): UiLinkPool {
    return UiLinkPool(prev, next)
}
