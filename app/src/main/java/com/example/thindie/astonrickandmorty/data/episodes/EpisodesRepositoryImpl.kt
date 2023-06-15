package com.example.thindie.astonrickandmorty.data.episodes

import com.example.thindie.astonrickandmorty.data.OutSourceLogic
import com.example.thindie.astonrickandmorty.data.OutSourcedImplementationLogic
import com.example.thindie.astonrickandmorty.data.episodesDbModelToDomain
import com.example.thindie.astonrickandmorty.data.episodesDomainToDbModel
import com.example.thindie.astonrickandmorty.data.episodesDtoToDomain
import com.example.thindie.astonrickandmorty.data.episodesResponseToDomain
import com.example.thindie.astonrickandmorty.data.localsource.EpisodesDao
import com.example.thindie.astonrickandmorty.data.localsource.entity.EpisodeDbModel
import com.example.thindie.astonrickandmorty.data.remotesource.EpisodesApi
import com.example.thindie.astonrickandmorty.data.remotesource.entity.episode.EpisodesDto
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeRepository
import javax.inject.Inject


class EpisodesRepositoryImpl @Inject constructor(
    private val api: EpisodesApi,
    private val dao: EpisodesDao
) : EpisodeRepository, OutSourcedImplementationLogic<EpisodeDomain, EpisodesDto, EpisodeDbModel> {
    private lateinit var outSourceLogic: OutSourceLogic<EpisodeDomain, EpisodesDto, EpisodeDbModel>

    init {
        OutSourceLogic.inject(this)
    }

    override suspend fun getAll(url: String?, idS: List<String>): Result<List<EpisodeDomain>> {
        return outSourceLogic.fetchAllAsResponse(
            mapper = episodesResponseToDomain,
            fetcher = {
                if (url == null) api.getAllEpisodes()
                else api.getBy(url)
            }
        )
            .onFailure {
                outSourceLogic.onFailedFetchMultiply(
                    mapper = episodesDbModelToDomain,
                    multiTaker = { dao.getAllEpisodes() }
                )

            }
            .onSuccess { /*things -> store(things)*/ }
    }

    override suspend fun getPoolOf(concretes: List<String>): Result<List<EpisodeDomain>> {
        return outSourceLogic.fetchAllAsDto(
            mapper = episodesDtoToDomain,
            fetcher = { param -> api.getByAsDto(param) },
            list = concretes
        )
            .onFailure {
                outSourceLogic.onFailedFetchConcrete(
                    mapper = episodesDbModelToDomain,
                    concreteReTaker = { dao.getAllEpisodes() }
                )
            }
            .onSuccess {

            }
    }

    override suspend fun getConcrete(id: Int): Result<EpisodeDomain> {
        return outSourceLogic.getConcrete(
            mapper = episodesDtoToDomain,
            fetcher = { api.getSingleEpisode(id) },
        )
            .onFailure {
                outSourceLogic.onFailedFetchConcrete(
                    mapper = episodesDbModelToDomain,
                    concreteReTaker = { dao.getAllEpisodes() }
                )
            }
    }


    override suspend fun store(things: List<EpisodeDomain>) {
        outSourceLogic.onLocalPost(list = things,
            mapper = episodesDomainToDbModel,
            poster = { list -> dao.upsertEpisodes(list) })
    }

    override fun setOutSource(logic: OutSourceLogic<EpisodeDomain, EpisodesDto, EpisodeDbModel>) {
        outSourceLogic = logic
    }


}


