package com.example.thindie.astonrickandmorty.ui.di

import androidx.lifecycle.ViewModel
import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesViewModel
import com.example.thindie.astonrickandmorty.ui.locations.LocationsViewModel
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
@Module
interface ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(EpisodesViewModel::class)
    fun bindEpisodesVM(episodesViewModel: EpisodesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PersonagesViewModel::class)
    fun bindPersonagesVM(personagesViewModel: PersonagesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationsViewModel::class)
    fun bindLocationsVM(locationsViewModel: LocationsViewModel): ViewModel


}