package com.example.thindie.astonrickandmorty.data.localsource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.thindie.astonrickandmorty.data.localsource.entity.LocationDbModel

@Dao
interface LocationsDao {

    @Query("SELECT * FROM $LOCATIONS")
    suspend fun getAllLocations(): List<LocationDbModel>

    @Query("SELECT * FROM $LOCATIONS WHERE id=:id LIMIT 1")
    suspend fun getConcreteLocation(id: Int): LocationDbModel

    @Upsert
    suspend fun upsertLocations(things: List<LocationDbModel>)

    @Query("SELECT * FROM $LOCATIONS WHERE name=:name")
    suspend fun getFilteredLocationsByName(name: String): List<LocationDbModel>

    @Query("SELECT * FROM $LOCATIONS WHERE type=:type")
    suspend fun getFilteredLocationsByType(type: String): List<LocationDbModel>

    @Query("SELECT * FROM $LOCATIONS WHERE dimension=:dimension")
    suspend fun getFilteredLocationsByDimension(dimension: String): List<LocationDbModel>
}

private const val LOCATIONS = "locations"