package com.example.thindie.astonrickandmorty.ui.di

import com.example.thindie.astonrickandmorty.data.DataLayerDependencyProvider
import com.example.thindie.astonrickandmorty.domain.di.DomainComponent
import dagger.Component


@Component(
    dependencies = [DataLayerDependencyProvider::class],
    modules = [ViewModelFactoryModule::class, ViewModelsModule::class]
)
interface ViewModelsComponent {
    companion object {
        fun install(component: DataLayerDependencyProvider): ViewModelsComponent {
            return DaggerViewModelsComponent.factory().create(component)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            component: DataLayerDependencyProvider
        ): ViewModelsComponent
    }
}