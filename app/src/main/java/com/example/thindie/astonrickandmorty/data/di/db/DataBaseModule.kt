package com.example.thindie.astonrickandmorty.data.di.db

import android.content.Context
import androidx.room.Room
import com.example.thindie.astonrickandmorty.data.localsource.AppDataBase
import com.example.thindie.astonrickandmorty.data.localsource.DataBaseUtil
import com.example.thindie.astonrickandmorty.data.localsource.EpisodesDao
import com.example.thindie.astonrickandmorty.data.localsource.LocationsDao
import com.example.thindie.astonrickandmorty.data.localsource.PersonagesDao
import dagger.Module
import dagger.Provides

@Module
object DataBaseModule {


    @Provides
    fun provideDataBase(context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            DataBaseUtil.dbName
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
