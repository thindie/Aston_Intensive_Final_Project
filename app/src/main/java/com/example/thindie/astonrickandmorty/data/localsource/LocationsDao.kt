package com.example.thindie.astonrickandmorty.data.localsource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.thindie.astonrickandmorty.data.localsource.entity.LocationDbModel

@Dao
interface LocationsDao {

    @Query("SELECT * FROM ${DataBaseUtil.locations}")
    suspend fun getAllLocations(): List<LocationDbModel>

    @Query("SELECT * FROM ${DataBaseUtil.locations} WHERE id=:id LIMIT 1")
    suspend fun getConcreteLocation(id: Int): LocationDbModel

    @Upsert
    suspend fun upsertLocations(things: List<LocationDbModel>)
}