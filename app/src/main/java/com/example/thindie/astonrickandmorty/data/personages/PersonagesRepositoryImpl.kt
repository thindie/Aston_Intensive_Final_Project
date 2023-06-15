package com.example.thindie.astonrickandmorty.data.personages

import com.example.thindie.astonrickandmorty.W
import com.example.thindie.astonrickandmorty.data.OutSourceLogic
import com.example.thindie.astonrickandmorty.data.OutSourcedImplementationLogic
import com.example.thindie.astonrickandmorty.data.localsource.PersonagesDao
import com.example.thindie.astonrickandmorty.data.localsource.entity.PersonageDbModel
import com.example.thindie.astonrickandmorty.data.personagesDbModelToDomain
import com.example.thindie.astonrickandmorty.data.personagesDomainToDbModel
import com.example.thindie.astonrickandmorty.data.personagesDtoToDomain
import com.example.thindie.astonrickandmorty.data.personagesResponseToDomain
import com.example.thindie.astonrickandmorty.data.remotesource.PersonagesApi
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageDto
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain
import com.example.thindie.astonrickandmorty.domain.personages.PersonageRepository
import javax.inject.Inject

class PersonagesRepositoryImpl @Inject constructor(
    private val api: PersonagesApi,
    private val dao: PersonagesDao
) : PersonageRepository,
    OutSourcedImplementationLogic<PersonageDomain, PersonageDto, PersonageDbModel> {
    private lateinit var outSourceLogic: OutSourceLogic<PersonageDomain, PersonageDto, PersonageDbModel>

    init {
        OutSourceLogic.inject(this)
    }

    override suspend fun getAll(url: String?, idS: List<String>): Result<List<PersonageDomain>> {
        return outSourceLogic.fetchAllAsResponse(
            mapper = personagesResponseToDomain,
            fetcher = {
                if (url == null) api.getAllCharacters()
                else api.getBy(url)
            }
        )
            .onFailure {
                outSourceLogic.onFailedFetchMultiply(
                    mapper = personagesDbModelToDomain,
                    multiTaker = { dao.getAllPersonages() }
                )

            }
            .onSuccess { /*things -> store(things)*/ }
    }

    override suspend fun getPoolOf(concretes: List<String>): Result<List<PersonageDomain>> {
        return outSourceLogic.fetchAllAsDto(
            mapper = personagesDtoToDomain,
            fetcher = { param -> api.getByAsDto(param) },
            list = concretes
        )
            .onFailure {
                outSourceLogic.onFailedFetchConcrete(
                    mapper = personagesDbModelToDomain,
                    concreteReTaker = { dao.getAllPersonages() }
                )
            }
    }

    override suspend fun getConcrete(id: Int): Result<PersonageDomain> {
        return outSourceLogic.getConcrete(
            mapper = personagesDtoToDomain,
            fetcher = { api.getSingleCharacter(id) },
        )
            .onFailure {
                W { it.message }
                outSourceLogic.onFailedFetchConcrete(
                    mapper = personagesDbModelToDomain,
                    concreteReTaker = { dao.getAllPersonages() }
                )
            }
    }

    override suspend fun store(things: List<PersonageDomain>) {
        outSourceLogic.onLocalPost(list = things,
            mapper = personagesDomainToDbModel,
            poster = { list -> dao.upsertPersonages(list) })
    }

    override fun setOutSource(logic: OutSourceLogic<PersonageDomain, PersonageDto, PersonageDbModel>) {
        outSourceLogic = logic
    }

}

