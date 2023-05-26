package com.example.thindie.astonrickandmorty.application.di

import android.content.Context
import com.example.thindie.astonrickandmorty.data.di.DataBaseModule
import com.example.thindie.astonrickandmorty.data.di.NetworkModule
import com.example.thindie.astonrickandmorty.data.episodes.EpisodesRepositoryImpl
import com.example.thindie.astonrickandmorty.data.locations.LocationsRepositoryImpl
import com.example.thindie.astonrickandmorty.data.personages.PersonagesRepositoryImpl
import com.example.thindie.astonrickandmorty.domain.BaseProvider
import com.example.thindie.astonrickandmorty.domain.BaseRepository
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeProvider
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeRepository
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodesFeatureUseCases
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.domain.locations.LocationProvider
import com.example.thindie.astonrickandmorty.domain.locations.LocationRepository
import com.example.thindie.astonrickandmorty.domain.locations.LocationsFeatureUseCases
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain
import com.example.thindie.astonrickandmorty.domain.personages.PersonageFeatureUseCases
import com.example.thindie.astonrickandmorty.domain.personages.PersonageProvider
import com.example.thindie.astonrickandmorty.domain.personages.PersonageRepository
import com.example.thindie.astonrickandmorty.ui.MainActivity
import com.example.thindie.astonrickandmorty.ui.basis.BaseFragment
import com.example.thindie.astonrickandmorty.ui.di.ViewModelsModule
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module

@Component
    (modules = [SourceModule::class, BindsInstances::class, ViewModelsModule::class])
interface AppComponent {
    companion object {
        fun install(context: Context): AppComponent {
            return DaggerAppComponent.factory()
                .create(context)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): AppComponent
    }

    fun inject(baseFragment: BaseFragment)
    fun inject(activity: MainActivity)
}

@Module(includes = [DataBaseModule::class, NetworkModule::class])
class SourceModule

@Module(includes = [RepositoryModule::class])
interface BindsInstances {

    @Binds
    fun bindEpisodesProvider(useCases: EpisodesFeatureUseCases): EpisodeProvider

    @Binds
    fun bindLocationsProvider(useCases: LocationsFeatureUseCases): LocationProvider

    @Binds
    fun bindPersonageProvider(useCases: PersonageFeatureUseCases): PersonageProvider

    @Binds
    fun bindEpisodesBaseProvider(provider: EpisodeProvider): BaseProvider<EpisodeDomain>

    @Binds
    fun bindLocationsBaseProvider(provider: LocationProvider): BaseProvider<LocationDomain>

    @Binds
    fun bindPersonageBaseProvider(provider: PersonageProvider): BaseProvider<PersonageDomain>
}

@Module
interface RepositoryModule {
    @Binds
    fun bindsRepoImpl(impl: LocationsRepositoryImpl): LocationRepository

    @Binds
    fun bindRepoIml(impl: EpisodesRepositoryImpl): EpisodeRepository

    @Binds
    fun bindRepoImpl(impl: PersonagesRepositoryImpl): PersonageRepository

    @Binds
    fun bindEpisodesBaseProvider(provider: EpisodeRepository): BaseRepository<EpisodeDomain>

    @Binds
    fun bindLocationsBaseProvider(provider: LocationRepository): BaseRepository<LocationDomain>

    @Binds
    fun bindPersonageBaseProvider(provider: PersonageRepository): BaseRepository<PersonageDomain>
}
