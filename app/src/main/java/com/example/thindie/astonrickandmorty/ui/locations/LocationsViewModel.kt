package com.example.thindie.astonrickandmorty.ui.locations

import com.example.thindie.astonrickandmorty.domain.BaseProvider
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.ui.basis.BaseViewModel
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer

class LocationsViewModel : BaseViewModel<LocationDomain>(), SearchEngineResultConsumer {
    override val provider: BaseProvider<LocationDomain>
        get() = TODO("Not yet implemented")

    override fun SearchAble.transform(): LocationDomain {
        TODO("Not yet implemented")
    }

    override fun onSearchResult(resultList: List<SearchAble>) {
        onSearch(resultList)
    }
}
