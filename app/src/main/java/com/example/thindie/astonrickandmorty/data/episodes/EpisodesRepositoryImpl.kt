package com.example.thindie.astonrickandmorty.data.episodes

import com.example.thindie.astonrickandmorty.data.getAndResult
import com.example.thindie.astonrickandmorty.data.localsource.EpisodesDao
import com.example.thindie.astonrickandmorty.data.remotesource.EpisodesApi
import com.example.thindie.astonrickandmorty.data.toEpisodeDbModel
import com.example.thindie.astonrickandmorty.data.toEpisodesDomain
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeRepository
import javax.inject.Inject


class EpisodesRepositoryImpl @Inject constructor(
    private val api: EpisodesApi,
    private val dao: EpisodesDao
) : EpisodeRepository {
    override suspend fun getAll(): Result<List<EpisodeDomain>> {
        return getAndResult {
            api.getAllEpisodes()
        }.mapCatching { rawDto ->
            rawDto.results.map { episodesDto ->
                episodesDto.toEpisodesDomain()
            }
        }
    }

    override suspend fun getConcrete(id: Int): Result<EpisodeDomain> {
        return getAndResult {
            api.getSingleEpisode(id)
        }.mapCatching { episodesDto ->
            episodesDto.toEpisodesDomain()
        }
    }

    override suspend fun retakeAll(): List<EpisodeDomain> {
        return dao.getAllEpisodes()
            .map { episodeDbModel ->
                episodeDbModel.toEpisodesDomain()
            }
    }

    override suspend fun retakeConcrete(id: Int): EpisodeDomain {
        return dao.getConcreteEpisode(id)
            .toEpisodesDomain()
    }

    override suspend fun store(things: List<EpisodeDomain>) {
        dao.upsertEpisodes(things.map { episodeDomain ->
            episodeDomain.toEpisodeDbModel()
        })
    }


}


