package com.example.thindie.astonrickandmorty.data.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class DispatchersIOModule {

    @IODispatcher
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class IODispatcher
}

@Module
class DispatchersMainModule {

    @MainDispatcher
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class MainDispatcher
}

@Module(includes = [DispatchersMainModule::class, DispatchersIOModule::class])
class CoroutinesScopeModule {

    @Singleton
    @Provides
    fun provideCoroutineScope(@DispatchersIOModule.IODispatcher IO: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(
            SupervisorJob() + IO
        )
}
