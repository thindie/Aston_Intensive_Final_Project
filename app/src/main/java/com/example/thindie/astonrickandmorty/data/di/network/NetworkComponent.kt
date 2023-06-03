package com.example.thindie.astonrickandmorty.data.di.network


import dagger.Component

@Component(
    modules = [NetworkModule::class]
)
interface NetworkComponent : NetworkProvider {
    companion object {
        fun install(): NetworkComponent {
            return DaggerNetworkComponent.factory().create()
        }
    }

    @Component.Factory
    interface Factory {
        fun create(): NetworkComponent
    }
}