package com.example.thindie.astonrickandmorty.ui.di

import com.example.thindie.astonrickandmorty.data.DataLayerDependencyProvider
import com.example.thindie.astonrickandmorty.domain.di.DomainComponent
import dagger.Component


@Component(
    dependencies = [DataLayerDependencyProvider::class, DomainComponent::class],
    modules = [ViewModelFactoryModule::class]
)
interface ViewModelsComponent {
    companion object {
        fun install(component: DataLayerDependencyProvider, domainComponent: DomainComponent): ViewModelsComponent {
            return DaggerViewModelsComponent.factory().create(component, domainComponent)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            component: DataLayerDependencyProvider,
            domainComponent: DomainComponent
        ): ViewModelsComponent
    }
}