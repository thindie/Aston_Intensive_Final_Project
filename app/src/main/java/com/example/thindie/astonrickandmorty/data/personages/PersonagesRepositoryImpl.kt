package com.example.thindie.astonrickandmorty.data.personages

import com.example.thindie.astonrickandmorty.data.assistGet
import com.example.thindie.astonrickandmorty.data.localsource.PersonagesDao
import com.example.thindie.astonrickandmorty.data.remotesource.PersonagesApi
import com.example.thindie.astonrickandmorty.data.toPersonageDomain
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain
import com.example.thindie.astonrickandmorty.domain.personages.PersonageRepository
import javax.inject.Inject

class PersonagesRepositoryImpl @Inject constructor(
    private val api: PersonagesApi,
    private val personagesDao: PersonagesDao
) : PersonageRepository {
    override suspend fun getAll(): Result<List<PersonageDomain>> {
        return assistGet {
            api.getAllCharacters()
        }.mapCatching { dto ->
            dto.results.map { personageDto ->
                personageDto.toPersonageDomain()
            }
        }
    }

    override suspend fun getConcrete(id: Int): Result<PersonageDomain> {
        return assistGet {
            api.getSingleCharacter(id)
        }.mapCatching { dto ->
            dto.toPersonageDomain()
        }
    }

}

