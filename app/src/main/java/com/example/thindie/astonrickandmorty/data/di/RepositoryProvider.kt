package com.example.thindie.astonrickandmorty.data.di

import com.example.thindie.astonrickandmorty.data.di.db.DaoProvider
import com.example.thindie.astonrickandmorty.data.di.network.NetworkProvider
import com.example.thindie.astonrickandmorty.domain.BaseRepository
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain

interface RepositoryProvider : DaoProvider, NetworkProvider {
    fun provideLocationsRepository(): BaseRepository<LocationDomain>
    fun provideEpisodeRepository(): BaseRepository<EpisodeDomain>
    fun providePersonageRepository(): BaseRepository<PersonageDomain>
}
