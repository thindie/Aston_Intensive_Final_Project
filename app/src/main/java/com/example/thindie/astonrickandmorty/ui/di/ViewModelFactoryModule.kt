package com.example.thindie.astonrickandmorty.ui.di

import androidx.lifecycle.ViewModel
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.domain.filtering.CharacterFilter
import com.example.thindie.astonrickandmorty.domain.filtering.EpisodeFilter
import com.example.thindie.astonrickandmorty.domain.filtering.LocationsFilter
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain
import com.example.thindie.astonrickandmorty.ui.basis.BaseViewModel
import com.example.thindie.astonrickandmorty.ui.basis.ViewModelsFactory
import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesViewModel
import com.example.thindie.astonrickandmorty.ui.locations.LocationsViewModel
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
class ViewModelFactoryModule {

    @Provides
    fun provideViewModelFactory(creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelsFactory {
        return ViewModelsFactory(creators)
    }

    /*@Provides
    fun <T, R> provideVM(baseViewModel: BaseViewModel<T, R>): ViewModel {
        return baseViewModel
    }*/


    @Provides
    @IntoMap
    @ViewModelKey(EpisodesViewModel::class)
    fun bindEpisodesVM(episodesViewModel: EpisodesViewModel): BaseViewModel<EpisodeDomain, EpisodeFilter> {
        return episodesViewModel
    }

    @Provides
    @IntoMap
    @ViewModelKey(PersonagesViewModel::class)
    fun bindPersonagesVM(personagesViewModel: PersonagesViewModel): BaseViewModel<PersonageDomain, CharacterFilter> {
        return personagesViewModel
    }

    @Provides
    @IntoMap
    @ViewModelKey(LocationsViewModel::class)
    fun bindLocationsVM(locationsViewModel: LocationsViewModel): BaseViewModel<LocationDomain, LocationsFilter> {
        return locationsViewModel
    }

}