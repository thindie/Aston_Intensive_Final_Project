package com.example.thindie.astonrickandmorty.data.di

import android.content.Context
import com.example.thindie.astonrickandmorty.data.di.db.DataBaseComponent
import com.example.thindie.astonrickandmorty.data.di.network.NetworkComponent
import dagger.Component

@Component(
    dependencies = [DataBaseComponent::class, NetworkComponent::class],
    modules = [BaseRepositoryModule::class]
)
interface RepositoryComponent : RepositoryProvider {
    companion object {
        fun install(context: Context): RepositoryComponent {
            val network = NetworkComponent.install()
            val dataBase = DataBaseComponent.install(context = context)
            return DaggerRepositoryComponent.factory().create(network, dataBase)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            networkProvider: NetworkComponent,
            daoProvider: DataBaseComponent
        ): RepositoryComponent
    }
}