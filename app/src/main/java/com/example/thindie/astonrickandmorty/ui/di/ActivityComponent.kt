package com.example.thindie.astonrickandmorty.ui.di

import com.example.thindie.astonrickandmorty.data.DataLayerDependencyProvider
import com.example.thindie.astonrickandmorty.ui.MainActivity
import dagger.Component

@Component
    (
    dependencies = [DataLayerDependencyProvider::class]
)
interface ActivityComponent {
    companion object {
        fun install(
            dependencyProvider: DataLayerDependencyProvider
        ): ActivityComponent {
            return DaggerActivityComponent.factory()
                .create(dependencyProvider)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            dependenciesProvider: DataLayerDependencyProvider
        ): ActivityComponent
    }

    fun inject(activity: MainActivity)
}