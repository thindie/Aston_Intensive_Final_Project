package com.example.thindie.astonrickandmorty.domain.di

import com.example.thindie.astonrickandmorty.domain.BaseProvider
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeProvider
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodesFeatureUseCases
import com.example.thindie.astonrickandmorty.domain.filtering.CharacterFilter
import com.example.thindie.astonrickandmorty.domain.filtering.EpisodeFilter
import com.example.thindie.astonrickandmorty.domain.filtering.LocationsFilter
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.domain.locations.LocationProvider
import com.example.thindie.astonrickandmorty.domain.locations.LocationsFeatureUseCases
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain
import com.example.thindie.astonrickandmorty.domain.personages.PersonageFeatureUseCases
import com.example.thindie.astonrickandmorty.domain.personages.PersonageProvider
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @Binds
    fun bindEpisodesProvider(useCases: EpisodesFeatureUseCases): EpisodeProvider

    @Binds
    fun bindLocationsProvider(useCases: LocationsFeatureUseCases): LocationProvider

    @Binds
    fun bindPersonageProvider(useCases: PersonageFeatureUseCases): PersonageProvider
}

@Module (includes = [DomainModule::class])
interface BaseDomainProviderModule {
    @Binds
    fun bindEpisodesBaseProvider(provider: EpisodeProvider): BaseProvider<EpisodeDomain, EpisodeFilter>

    @Binds
    fun bindLocationsBaseProvider(provider: LocationProvider): BaseProvider<LocationDomain, LocationsFilter>

    @Binds
    fun bindPersonageBaseProvider(provider: PersonageProvider): BaseProvider<PersonageDomain, CharacterFilter>
}