package com.example.thindie.astonrickandmorty.ui.locations

import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import com.example.thindie.astonrickandmorty.domain.filtering.LocationsFilter
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.domain.locations.LocationProvider
import com.example.thindie.astonrickandmorty.ui.basis.BaseViewModel
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer
import javax.inject.Inject

class LocationsViewModel @Inject constructor(private val provider: LocationProvider) :
    BaseViewModel<LocationDomain, LocationsFilter>(provider),
    SearchEngineResultConsumer {

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
