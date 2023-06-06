package com.example.thindie.astonrickandmorty.domain.di

import com.example.thindie.astonrickandmorty.data.DataLayerDependencyProvider
import dagger.Component

@Component
    (
    dependencies = [DataLayerDependencyProvider::class],
    modules = [BaseDomainProviderModule::class]
)
interface DomainComponent {

    companion object {
        fun install(repositoryProvider: DataLayerDependencyProvider): DomainComponent {
            return DaggerDomainComponent.factory().create(repositoryProvider)
        }

    }

    @Component.Factory
    interface Factory {
        fun create(repositoryProvider: DataLayerDependencyProvider): DomainComponent
    }

}