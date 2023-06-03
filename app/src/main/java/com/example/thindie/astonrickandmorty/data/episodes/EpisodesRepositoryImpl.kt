package com.example.thindie.astonrickandmorty.data.episodes

import com.example.thindie.astonrickandmorty.data.assistGet
import com.example.thindie.astonrickandmorty.data.localsource.EpisodesDao
import com.example.thindie.astonrickandmorty.data.remotesource.EpisodesApi
import com.example.thindie.astonrickandmorty.data.toEpisodesDomain
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeRepository
import javax.inject.Inject


class EpisodesRepositoryImpl @Inject constructor(
    private val episodesApi: EpisodesApi,
    private val dao: EpisodesDao
) :
    EpisodeRepository {
    override suspend fun getAll(): Result<List<EpisodeDomain>> {
        return assistGet {
            episodesApi.getAllEpisodes()
        }.mapCatching { dto ->
            dto.results.map { episodesDto ->
                episodesDto.toEpisodesDomain()
            }
        }
    }

    override suspend fun getConcrete(id: Int): Result<EpisodeDomain> {
        return assistGet {
            episodesApi.getSingleEpisode(id)
        }.mapCatching { dto ->
            dto.toEpisodesDomain()
        }
    }

}