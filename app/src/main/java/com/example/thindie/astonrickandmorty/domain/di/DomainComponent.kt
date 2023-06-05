package com.example.thindie.astonrickandmorty.domain.di

import com.example.thindie.astonrickandmorty.data.DataLayerDependencyProvider
import dagger.Component

@Component
    (
    dependencies = [DataLayerDependencyProvider::class],
    modules = [DomainModule::class]
)
interface DomainComponent {

    companion object {
        fun  install(dependencyProvider: DataLayerDependencyProvider): DomainComponent {
            return DaggerDomainComponent.factory().create(dependencyProvider)

        }

    }

    @Component.Factory
    interface Factory {
        fun create(dependencyProvider: DataLayerDependencyProvider): DomainComponent
    }

}