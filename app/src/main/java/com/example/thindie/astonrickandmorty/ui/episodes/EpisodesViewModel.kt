package com.example.thindie.astonrickandmorty.ui.episodes

import com.example.thindie.astonrickandmorty.domain.BaseProvider
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.ui.basis.BaseViewModel
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer

class EpisodesViewModel : BaseViewModel<EpisodeDomain>(), SearchEngineResultConsumer {
    override val provider: BaseProvider<EpisodeDomain>
        get() = TODO("Not yet implemented")

    override fun SearchAble.transform(): EpisodeDomain {
        TODO("Not yet implemented")
    }

    override fun onSearchResult(resultList: List<SearchAble>) {
        onSearch(resultList)
    }


}