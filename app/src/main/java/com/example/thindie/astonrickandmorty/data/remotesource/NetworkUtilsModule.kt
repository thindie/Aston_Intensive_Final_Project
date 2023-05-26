package com.example.thindie.astonrickandmorty.data.remotesource

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 * API HERE:
 * https://rickandmortyapi.com/documentation/#introduction
 *
 **/

internal object NetworkUtilsModule {

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

    fun provideOkhttp(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(interceptor)
            .build()
    }
}
