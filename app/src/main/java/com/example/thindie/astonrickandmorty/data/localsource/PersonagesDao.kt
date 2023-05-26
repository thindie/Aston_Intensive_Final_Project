package com.example.thindie.astonrickandmorty.data.localsource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.thindie.astonrickandmorty.data.localsource.entity.PersonageDbModel

@Dao
interface PersonagesDao {

    @Query("SELECT * FROM  $PERSONAGES")
    suspend fun getAllPersonages(): List<PersonageDbModel>

    @Query("SELECT * FROM  $PERSONAGES WHERE id=:id LIMIT 1")
    suspend fun getConcretePersonage(id: Int): PersonageDbModel

    @Upsert
    suspend fun upsertPersonages(things: List<PersonageDbModel>)

    @Query("SELECT * FROM $PERSONAGES WHERE name=:name")
    suspend fun getFilteredLocationsByName(name: String): List<PersonageDbModel>

    @Query("SELECT * FROM $PERSONAGES WHERE status=:status")
    suspend fun getFilteredLocationsByStatus(status: String): List<PersonageDbModel>

    @Query("SELECT * FROM $PERSONAGES WHERE species=:species")
    suspend fun getFilteredLocationsBySpecies(species: String): List<PersonageDbModel>

    @Query("SELECT * FROM $PERSONAGES WHERE type=:type")
    suspend fun getFilteredLocationsByType(type: String): List<PersonageDbModel>

    @Query("SELECT * FROM $PERSONAGES WHERE gender=:gender")
    suspend fun getFilteredLocationsByDimension(gender: String): List<PersonageDbModel>
}
private const val PERSONAGES = "personages"
