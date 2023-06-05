package com.example.thindie.astonrickandmorty.ui.di

import androidx.lifecycle.ViewModelProvider
import com.example.thindie.astonrickandmorty.ui.basis.ViewModelsFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {
    @Binds
    fun bindViewModelFactory(factoryModule: ViewModelsFactory): ViewModelProvider.Factory
}