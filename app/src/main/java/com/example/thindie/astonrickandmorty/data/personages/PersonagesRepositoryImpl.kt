package com.example.thindie.astonrickandmorty.data.personages

import com.example.thindie.astonrickandmorty.data.getAndResult
import com.example.thindie.astonrickandmorty.data.localsource.PersonagesDao
import com.example.thindie.astonrickandmorty.data.remotesource.PersonagesApi
import com.example.thindie.astonrickandmorty.data.toPersonageDbModel
import com.example.thindie.astonrickandmorty.data.toPersonageDomain
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain
import com.example.thindie.astonrickandmorty.domain.personages.PersonageRepository
import javax.inject.Inject

class PersonagesRepositoryImpl @Inject constructor(
    private val api: PersonagesApi,
    private val dao: PersonagesDao
) : PersonageRepository {
    override suspend fun getAll(): Result<List<PersonageDomain>> {
        return getAndResult {
            api.getAllCharacters()
        }.mapCatching { rawPersonage ->
            rawPersonage.results.map { personageDto ->
                personageDto.toPersonageDomain()
            }
        }
    }

    override suspend fun getConcrete(id: Int): Result<PersonageDomain> {
        return getAndResult {
            api.getSingleCharacter(id)
        }.mapCatching { personageDto ->
            personageDto.toPersonageDomain()

        }
    }

    override suspend fun retakeAll(): List<PersonageDomain> {
        return dao.getAllPersonages()
            .map { personageDbModel ->
                personageDbModel.toPersonageDomain()
            }
    }

    override suspend fun retakeConcrete(id: Int): PersonageDomain {
        return dao.getConcretePersonage(id).toPersonageDomain()
    }

    override suspend fun store(things: List<PersonageDomain>) {
        dao.upsertPersonages(things.map { personageDomain ->
            personageDomain.toPersonageDbModel()
        })
    }


}

