package com.example.thindie.astonrickandmorty.ui.personage

import com.example.thindie.astonrickandmorty.domain.BaseProvider
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain
import com.example.thindie.astonrickandmorty.ui.basis.BaseViewModel
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer

class PersonagesViewModel : BaseViewModel<PersonageDomain>(), SearchEngineResultConsumer {
    override val provider: BaseProvider<PersonageDomain>
        get() = TODO("Not yet implemented")

    override fun SearchAble.transform(): PersonageDomain {
        TODO("Not yet implemented")
    }

    override fun onSearchResult(resultList: List<SearchAble>) {
        onSearch(resultList)
    }
}