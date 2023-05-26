package com.example.thindie.astonrickandmorty.data.localsource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.thindie.astonrickandmorty.data.localsource.entity.BaseListConverter
import com.example.thindie.astonrickandmorty.data.localsource.entity.EpisodeDbModel
import com.example.thindie.astonrickandmorty.data.localsource.entity.LinkPoolConverter
import com.example.thindie.astonrickandmorty.data.localsource.entity.LocationDbModel
import com.example.thindie.astonrickandmorty.data.localsource.entity.LocationPersonageConverter
import com.example.thindie.astonrickandmorty.data.localsource.entity.OriginPersonageConverter
import com.example.thindie.astonrickandmorty.data.localsource.entity.PersonageDbModel

@Database(
    entities = [PersonageDbModel::class, EpisodeDbModel::class, LocationDbModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    BaseListConverter::class,
    LinkPoolConverter::class,
    OriginPersonageConverter::class,
    LocationPersonageConverter::class
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getEpisodesDao(): EpisodesDao
    abstract fun getLocationsDao(): LocationsDao
    abstract fun getPersonagesDao(): PersonagesDao
}
