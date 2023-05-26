package com.example.thindie.astonrickandmorty.data.remotesource

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class RetrofitNetwork  constructor(
    okHttpClient: OkHttpClient,
)   {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)



    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api"
    }


}