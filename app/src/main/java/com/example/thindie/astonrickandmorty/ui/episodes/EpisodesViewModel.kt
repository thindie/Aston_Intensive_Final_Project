package com.example.thindie.astonrickandmorty.ui.episodes

import com.example.thindie.astonrickandmorty.domain.BaseProvider
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.domain.filtering.EpisodeFilter
import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import com.example.thindie.astonrickandmorty.ui.basis.BaseViewModel
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer

class EpisodesViewModel : BaseViewModel<EpisodeDomain, EpisodeFilter>(), SearchEngineResultConsumer {
    override val provider: BaseProvider<EpisodeDomain, EpisodeFilter>
        get() = TODO("Not yet implemented")

    override fun applyFilter(): Filter<EpisodeDomain, EpisodeFilter> {
        return EpisodeFilter()
    }

    override fun SearchAble.transform(): EpisodeDomain {
        TODO("Not yet implemented")
    }

    override fun onSearchResult(resultList: List<SearchAble>) {
        onSearch(resultList)
    }


}