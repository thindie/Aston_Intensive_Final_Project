package com.example.thindie.astonrickandmorty.data.di

import android.content.Context
import androidx.room.Room
import com.example.thindie.astonrickandmorty.data.localsource.AppDataBase
import com.example.thindie.astonrickandmorty.data.localsource.EpisodesDao
import com.example.thindie.astonrickandmorty.data.localsource.LocationsDao
import com.example.thindie.astonrickandmorty.data.localsource.PersonagesDao
import dagger.Module
import dagger.Provides

private const val DB_NAME = "rickMortyDb"

@Module
class DataBaseModule {

    @Provides
    fun provideDataBase(context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    fun provideEpisodesDao(appDataBase: AppDataBase): EpisodesDao {
        return appDataBase.getEpisodesDao()
    }

    @Provides
    fun provideLocationsDao(appDataBase: AppDataBase): LocationsDao {
        return appDataBase.getLocationsDao()
    }

    @Provides
    fun providePersonagesDao(appDataBase: AppDataBase): PersonagesDao {
        return appDataBase.getPersonagesDao()
    }
}
