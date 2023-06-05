package com.example.thindie.astonrickandmorty.domain.di

import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeProvider
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodesFeatureUseCases
import com.example.thindie.astonrickandmorty.domain.locations.LocationProvider
import com.example.thindie.astonrickandmorty.domain.locations.LocationsFeatureUseCases
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