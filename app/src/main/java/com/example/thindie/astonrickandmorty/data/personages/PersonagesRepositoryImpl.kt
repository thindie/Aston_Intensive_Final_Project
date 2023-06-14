package com.example.thindie.astonrickandmorty.data.personages

import android.util.Log
import com.example.thindie.astonrickandmorty.data.OutSourceLogic
import com.example.thindie.astonrickandmorty.data.OutSourcedImplementationLogic
import com.example.thindie.astonrickandmorty.data.localsource.PersonagesDao
import com.example.thindie.astonrickandmorty.data.localsource.entity.PersonageDbModel
import com.example.thindie.astonrickandmorty.data.personagesDbModelToDomain
import com.example.thindie.astonrickandmorty.data.personagesDtoToDomain
import com.example.thindie.astonrickandmorty.data.personagesResponseToDomain
import com.example.thindie.astonrickandmorty.data.remotesource.PersonagesApi
import com.example.thindie.astonrickandmorty.data.remotesource.entity.personage.PersonageDto
import com.example.thindie.astonrickandmorty.data.remotesource.util.commaQueryEncodedBuilder
import com.example.thindie.astonrickandmorty.data.toPersonageDbModel
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
        return outSourceLogic.fetchAllAsResponse(personagesResponseToDomain) {
            Log.d("SERVICE_TAG_rep", url.toString())
            if (url == null)
                api.getAllCharacters()
            else api.getBy(url)
        }
            .onFailure {
                outSourceLogic.onFailedFetchMultiply(personagesDbModelToDomain)
                { dao.getAllPersonages() }
            }
            .onSuccess { /*things -> store(things)*/ }
    }

    override suspend fun getConcrete(concretes: List<String>): Result<List<PersonageDomain>> {
        return outSourceLogic.getConcrete(concretes, personagesDtoToDomain) { path ->
            api.getMultiply(path.toInt())
        }
            .onFailure {
                outSourceLogic.onFailedFetchConcrete(personagesDbModelToDomain)
                { dao.getAllPersonages() }
            }
            .onSuccess { }
    }

    override suspend fun store(things: List<PersonageDomain>) {
        dao.upsertPersonages(things.map { personageDomain ->
            personageDomain.toPersonageDbModel()
        })
    }

    override fun setOutSource(logic: OutSourceLogic<PersonageDomain, PersonageDto, PersonageDbModel>) {
        outSourceLogic = logic
    }

    private fun List<String>.buildUrl(): String {
        if (size > 1)
            map { fullLinkWithIdEnded ->
                fullLinkWithIdEnded.substringAfterLast("/")
            }
                .map { idWith ->
                    idWith.removeSurrounding("/")
                }.apply {
                    return CHARACTER
                        .plus("/")
                        .plus(commaQueryEncodedBuilder(this))
                }
       return first()
    }

    companion object {
        private const val ENDPOINT = "https://rickandmortyapi.com/api/"
        private const val CHARACTER = "${ENDPOINT}character"
        private const val LOCATION = "${ENDPOINT}location"
        private const val EPISODE = "${ENDPOINT}episode"
        const val EPISODE_ID = "${EPISODE}/{id}"
        const val CHARACTER_ID = "${CHARACTER}/{id}"
        const val LOCATION_ID = "${LOCATION}/{id}"
    }


}

