package com.example.thindie.astonrickandmorty.data.episodes

import com.example.thindie.astonrickandmorty.data.OutSourceLogic
import com.example.thindie.astonrickandmorty.data.OutSourcedImplementationLogic
import com.example.thindie.astonrickandmorty.data.di.DispatchersIOModule
import com.example.thindie.astonrickandmorty.data.episodesDbModelToDomain
import com.example.thindie.astonrickandmorty.data.episodesDomainToDbModel
import com.example.thindie.astonrickandmorty.data.episodesDtoToDomain
import com.example.thindie.astonrickandmorty.data.episodesResponseToDomain
import com.example.thindie.astonrickandmorty.data.localsource.EpisodesDao
import com.example.thindie.astonrickandmorty.data.localsource.entity.EpisodeDbModel
import com.example.thindie.astonrickandmorty.data.remotesource.EpisodesApi
import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesDto
import com.example.thindie.astonrickandmorty.data.remotesource.util.commaQueryEncodedBuilder
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeRepository
import com.example.thindie.astonrickandmorty.ui.uiutils.qq
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(
    private val api: EpisodesApi,
    private val dao: EpisodesDao,
    @DispatchersIOModule.IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : EpisodeRepository, OutSourcedImplementationLogic<EpisodeDomain, EpisodesDto, EpisodeDbModel> {
    private lateinit var outSourceLogic: OutSourceLogic<EpisodeDomain, EpisodesDto, EpisodeDbModel>

    private var isActualiseNeeded = true
    private var isNetworkActive = false

    init {
        OutSourceLogic.inject(this)
    }

    override suspend fun getAll(url: String?): Result<List<EpisodeDomain>> {
        return outSourceLogic.fetchAllAsResponse(
            mapper = episodesResponseToDomain,
            fetcher = {
                if (url == null) {
                    api.getAllEpisodes()
                } else api.getBy(url)
            }
        )
            .onFailure {
                outSourceLogic.onFailedFetchMultiply(
                    mapper = episodesDbModelToDomain,
                    multiTaker = {
                        qq { url }
                        outSourceLogic.onFailPaginateFetchFromWeb(
                            url = { url },
                            localFetcher = { id -> dao.getConcreteEpisode(id) },
                        )
                    }
                )
                    .onFailure {
                        return Result.failure(it)
                    }
                    .onSuccess {
                        return Result.success(it)
                    }
            }
            .onSuccess { things ->
                withContext(ioDispatcher) {
                    store(things)
                }
            }
    }

    override suspend fun getPoolOf(concretes: List<String>): Result<List<EpisodeDomain>> {
        qq { "on pool of" }
        val link = concretes
            .map { link -> link.substringAfterLast("/") }

        return kotlin
            .runCatching {
                api.getMultiEpisode(commaQueryEncodedBuilder(link))
            }
            .mapCatching { list ->
                list.map(episodesDtoToDomain)
            }
            .onFailure {
                outSourceLogic.onFailedFetchMultiply(
                    mapper = episodesDbModelToDomain,
                    multiTaker = {
                        outSourceLogic.onFailedFetchGroupOfLinks(
                            linksList = concretes,
                            localFetcher = { id -> dao.getConcreteEpisode(id) }
                        )
                    }
                )
                    .onFailure {
                        qq { "episodes on failure on return " }
                        return Result.failure(it)
                    }
                    .onSuccess {
                        return Result.success(it)
                    }
            }
            .onSuccess { things ->
                withContext(ioDispatcher) {
                    store(things)
                }
            }
    }

    override suspend fun getConcrete(id: Int): Result<EpisodeDomain> {
        return kotlin
            .runCatching { api.getSingleEpisode(id) }
            .mapCatching(episodesDtoToDomain)
            .onFailure {
                outSourceLogic.onFailedFetchConcrete(
                    mapper = episodesDbModelToDomain,
                    concreteReTaker = { dao.getConcreteEpisode(id) }
                )
                    .onFailure {
                        return Result.failure(it)
                    }
                    .onSuccess {
                        return Result.success(it)
                    }
            }
            .onSuccess { thing ->
                store(listOf(thing))
            }
    }

    override suspend fun store(things: List<EpisodeDomain>) {
        outSourceLogic.onLocalPost(
            list = things,
            mapper = episodesDomainToDbModel,
            poster = { list -> dao.upsertEpisodes(list) }
        )
    }

    override fun setOutSource(logic: OutSourceLogic<EpisodeDomain, EpisodesDto, EpisodeDbModel>) {
        outSourceLogic = logic
    }
}
