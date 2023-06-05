package com.example.thindie.astonrickandmorty.ui.personage

import com.example.thindie.astonrickandmorty.domain.BaseProvider
import com.example.thindie.astonrickandmorty.domain.filtering.CharacterFilter
import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain
import com.example.thindie.astonrickandmorty.ui.basis.BaseViewModel
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer

class PersonagesViewModel : BaseViewModel<PersonageDomain, CharacterFilter>(),
    SearchEngineResultConsumer {
    override val provider: BaseProvider<PersonageDomain, CharacterFilter>
        get() = TODO("Not yet implemented")

    override fun applyFilter(): Filter<PersonageDomain, CharacterFilter> {
        return CharacterFilter()
    }

    override fun SearchAble.transform(): PersonageDomain {
        TODO("Not yet implemented")
    }

    override fun onSearchResult(resultList: List<SearchAble>) {
        onSearch(resultList)
    }
}