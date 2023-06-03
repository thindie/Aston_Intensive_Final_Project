package com.example.thindie.astonrickandmorty.data.di.network

import com.example.thindie.astonrickandmorty.data.remotesource.EpisodesApi
import com.example.thindie.astonrickandmorty.data.remotesource.LocationApi
import com.example.thindie.astonrickandmorty.data.remotesource.PersonagesApi

interface NetworkProvider {
    fun provideEpisodesApi(): EpisodesApi

    fun provideLocationsApi(): LocationApi

    fun providePersonagesApi(): PersonagesApi
}
