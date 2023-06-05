package com.example.thindie.astonrickandmorty.domain.filtering

import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.domain.matchCriteria

data class EpisodeFilter(
    val name: String = DEFAULT_VALUE,
    val episode: String = DEFAULT_VALUE
) : Filter<EpisodeDomain, EpisodeFilter> {
    override val isDefault: Boolean
        get() = isDefaults()

    private fun isDefaults() =
        name == defaultValue && episode == defaultValue

    override fun detectActualFilteringCases(): EpisodeFilter {
        diffList.clear()
        if (name != defaultValue) diffList += 1
        if (episode != defaultValue) diffList += 2
        return this
    }

    override fun filter(list: List<EpisodeDomain>): List<EpisodeDomain> {
        val resultList = list.toMutableList()
         diffList.forEach { filter ->
            when (filter) {
                1 -> {
                    resultList.map { it.name.matchCriteria(name) }
                }
                2 -> {
                    resultList.map { it.episode.matchCriteria(episode) }
                }
            }
        }
        if (resultList.isEmpty()) error(unSuccessFiltering)
        return resultList
    }

    companion object {
        private val diffList: MutableList<Int> = mutableListOf()

        private const val DEFAULT_VALUE = ""

    }
}