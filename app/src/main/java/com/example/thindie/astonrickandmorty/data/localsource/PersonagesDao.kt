package com.example.thindie.astonrickandmorty.data.localsource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.thindie.astonrickandmorty.data.localsource.entity.PersonageDbModel

@Dao
interface PersonagesDao {

    @Query("SELECT * FROM ${DataBaseUtil.personages}")
      suspend fun getAllPersonages(): List<PersonageDbModel>

    @Query("SELECT * FROM ${DataBaseUtil.personages} WHERE id=:id LIMIT 1")
      suspend fun getConcretePersonage(id: Int): PersonageDbModel

    @Upsert
      suspend fun upsertPersonages(things: List<PersonageDbModel>)
}