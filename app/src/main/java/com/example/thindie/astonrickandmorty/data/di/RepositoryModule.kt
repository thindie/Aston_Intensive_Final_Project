package com.example.thindie.astonrickandmorty.data.di

import com.example.thindie.astonrickandmorty.data.episodes.EpisodesRepositoryImpl
import com.example.thindie.astonrickandmorty.data.locations.LocationsRepositoryImpl
import com.example.thindie.astonrickandmorty.data.personages.PersonagesRepositoryImpl
import com.example.thindie.astonrickandmorty.domain.BaseRepository
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeRepository
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.domain.locations.LocationRepository
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain
import com.example.thindie.astonrickandmorty.domain.personages.PersonageRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindLocationsRepository(impl: LocationsRepositoryImpl): LocationRepository

    @Binds
    fun bindPersonageRepository(impl: PersonagesRepositoryImpl): PersonageRepository


    @Binds
    fun bindEpisodesRepository(impl: EpisodesRepositoryImpl): EpisodeRepository

}

@Module
    (
    includes = [RepositoryModule::class]
)

interface BaseRepositoryModule {
    @Binds
    fun bindBaseRepositoryLocations(repository: LocationRepository): BaseRepository<LocationDomain>

    @Binds
    fun bindBaseRepositoryEpisodes(repository: EpisodeRepository): BaseRepository<EpisodeDomain>

    @Binds
    fun bindBaseRepositoryPersonages(repository: PersonageRepository): BaseRepository<PersonageDomain>

}