package com.example.thindie.astonrickandmorty.data.di.db

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [DataBaseModule::class]
)

interface DataBaseComponent : DaoProvider {
    companion object {
        fun install(context: Context): DataBaseComponent {
            return DaggerDataBaseComponent.factory().create(context)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DataBaseComponent
    }
}