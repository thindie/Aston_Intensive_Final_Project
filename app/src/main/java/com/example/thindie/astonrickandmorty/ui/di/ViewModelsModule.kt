package com.example.thindie.astonrickandmorty.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thindie.astonrickandmorty.ui.MainViewModel
import com.example.thindie.astonrickandmorty.ui.basis.EventConsumer
import com.example.thindie.astonrickandmorty.ui.basis.ViewModelFactory
import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesViewModel
import com.example.thindie.astonrickandmorty.ui.locations.LocationsViewModel
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class, EventConsumerModule::class])
class ViewModelsModule {

    @Provides
    @IntoMap
    @ViewModelKey(EpisodesViewModel::class)
    fun bindEpisodesVM(episodesViewModel: EpisodesViewModel): ViewModel {
        return episodesViewModel
    }

    @Provides
    @IntoMap
    @ViewModelKey(PersonagesViewModel::class)
    fun bindPersonagesVM(personagesViewModel: PersonagesViewModel): ViewModel {
        return personagesViewModel
    }

    @Provides
    @IntoMap
    @ViewModelKey(LocationsViewModel::class)
    fun bindLocationsVM(locationsViewModel: LocationsViewModel): ViewModel {
        return locationsViewModel
    }

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainVM(viewModel: MainViewModel): ViewModel {
        return viewModel
    }
}

@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindViewModelFactory(
        viewModelFactory: ViewModelFactory
    ): ViewModelProvider.Factory
}

@Module
interface EventConsumerModule {
    @Binds
    fun bindEventConsumer(viewModel: MainViewModel): EventConsumer<String>
}
