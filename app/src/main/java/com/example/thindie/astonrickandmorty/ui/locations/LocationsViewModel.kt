package com.example.thindie.astonrickandmorty.ui.locations

import com.example.thindie.astonrickandmorty.domain.BaseProvider
import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import com.example.thindie.astonrickandmorty.domain.filtering.LocationsFilter
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.ui.basis.BaseViewModel
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer

class LocationsViewModel : BaseViewModel<LocationDomain, LocationsFilter>(),
    SearchEngineResultConsumer {
    override val provider: BaseProvider<LocationDomain, LocationsFilter>
        get() = TODO("Not yet implemented")

    override fun applyFilter(): Filter<LocationDomain, LocationsFilter> {
        return LocationsFilter()
    }

    override fun SearchAble.transform(): LocationDomain {
        TODO("Not yet implemented")
    }

    override fun onSearchResult(resultList: List<SearchAble>) {
        onSearch(resultList)
    }
}
