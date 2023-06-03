package com.example.thindie.astonrickandmorty.data.di

import android.util.Log
import com.example.thindie.astonrickandmorty.data.remotesource.EpisodesApi
import com.example.thindie.astonrickandmorty.data.remotesource.LocationApi
import com.example.thindie.astonrickandmorty.data.remotesource.PersonagesApi
import com.example.thindie.astonrickandmorty.data.remotesource.util.ApiParams
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {


    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(ApiParams.ENDPOINT)
            .client(provideOkhttp())
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

}

fun provideInterceptor(): Interceptor {
    return Interceptor { chain ->
        val response = chain
            .proceed(chain.request())
        Log.d("SERVICE_TAG_INTERCEPTOR", ("Outgoing request to ${response.request.url}"))
        if (!response.isSuccessful) {
            Log.d("SERVICE_TAG_UNSUCCESSFUL_NETWORK", "${response.code} - response code")
        }
        response
    }
}

fun provideOkhttp(): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(provideInterceptor())
        .build()
}