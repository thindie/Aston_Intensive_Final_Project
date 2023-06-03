package com.example.thindie.astonrickandmorty.application.di

import android.content.Context
import com.example.thindie.astonrickandmorty.data.DataLayerDependencyProvider
import com.example.thindie.astonrickandmorty.data.di.RepositoryComponent
import com.example.thindie.astonrickandmorty.data.di.RepositoryProvider
import dagger.BindsInstance
import dagger.Component


@Component(
    dependencies = [RepositoryProvider::class],
)
interface AppComponent : DataLayerDependencyProvider {

    companion object {
        fun install(context: Context): AppComponent {
            val repositoryProvider = RepositoryComponent.install(context)
            return DaggerAppComponent.factory()
                .create(context, repositoryProvider)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            repositories: RepositoryProvider
        ): AppComponent
    }
}