package com.example.thindie.astonrickandmorty.data.di.db

import com.example.thindie.astonrickandmorty.data.localsource.EpisodesDao
import com.example.thindie.astonrickandmorty.data.localsource.LocationsDao
import com.example.thindie.astonrickandmorty.data.localsource.PersonagesDao

interface DaoProvider {
    fun provideEpisodesDao(): EpisodesDao

    fun provideLocationsDao(): LocationsDao

    fun providePersonagesDao(): PersonagesDao
}
