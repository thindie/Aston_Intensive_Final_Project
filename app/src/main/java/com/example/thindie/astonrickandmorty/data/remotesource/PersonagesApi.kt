package com.example.thindie.astonrickandmorty.data.remotesource

import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageDto
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageRaw
import com.example.thindie.astonrickandmorty.data.remotesource.util.ApiParams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PersonagesApi {


    @GET(ApiParams.CHARACTER)
    suspend fun getAllCharacters(): Response<PersonageRaw>

    @GET
    suspend fun getAllCharacterWithPage(@Url urlWithPage: String)

    @GET(ApiParams.CHARACTER_ID)
    suspend fun getSingleCharacter(
        @Path("id") characterId: Int
    ): Response<PersonageDto>


/*    @Suppress("LongParameterList")
    @GET(ApiParams.CHARACTER)
    suspend fun getCharactersFiltered(
        characterQueryModifier: CharacterQueryModifier,
        @Query(ApiParams.CHARACTER_NAME) name: String = characterQueryModifier.name,
        @Query(ApiParams.CHARACTER_STATUS) status: String = characterQueryModifier.status,
        @Query(ApiParams.CHARACTER_SPECIES) species: String = characterQueryModifier.species,
        @Query(ApiParams.CHARACTER_TYPE) type: String = characterQueryModifier.type,
        @Query(ApiParams.CHARACTER_GENDER) gender: String = characterQueryModifier.gender,
    )*/

}