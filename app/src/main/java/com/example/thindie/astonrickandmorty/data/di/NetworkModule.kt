package com.example.thindie.astonrickandmorty.data.di

import com.example.thindie.astonrickandmorty.data.remotesource.EpisodesApi
import com.example.thindie.astonrickandmorty.data.remotesource.LocationApi
import com.example.thindie.astonrickandmorty.data.remotesource.PersonagesApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [CoroutinesScopeModule::class])
class NetworkModule {

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(ENDPOINT)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideEpisodesApi(retrofit: Retrofit): EpisodesApi {
        return retrofit.create(EpisodesApi::class.java)
    }

    @Provides
    fun provideLocationsApi(retrofit: Retrofit): LocationApi {
        return retrofit.create(LocationApi::class.java)
    }

    @Provides
    fun providePersonagesApi(retrofit: Retrofit): PersonagesApi {
        return retrofit.create(PersonagesApi::class.java)
    }

    @Provides
    fun provideOkhttp(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .build()
    }
}

private const val ENDPOINT = "https://rickandmortyapi.com/api/"
