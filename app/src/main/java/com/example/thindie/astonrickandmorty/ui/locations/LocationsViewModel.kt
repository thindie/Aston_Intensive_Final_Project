package com.example.thindie.astonrickandmorty.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import com.example.thindie.astonrickandmorty.domain.filtering.LocationsFilter
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.domain.locations.LocationProvider
import com.example.thindie.astonrickandmorty.ui.basis.mappers.mapLocationDomain
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutSourced
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutsourceLogic
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer
import javax.inject.Inject
import kotlinx.coroutines.launch

class LocationsViewModel @Inject constructor(private val provider: LocationProvider) : ViewModel(),
    SearchEngineResultConsumer, OutSourced<LocationDomain, LocationsFilter> {

    private lateinit var outSource: OutsourceLogic<LocationDomain, LocationsFilter>

    init {
        OutsourceLogic.inject(this, provider)
    }


    val viewState: LiveData<OutsourceLogic.UiState>
        get() = outSource.observable


    fun onClickedNavigationButton() {
        viewModelScope.launch {
            outSource.fetchAll(mapLocationDomain) {
                onApplyFilter()
            }
        }
    }

    fun clickConcrete(id: Int) {
        viewModelScope.launch {
            outSource.fetchConcrete(id, mapLocationDomain)
        }
    }

    fun onApplyFilter(
        name: String = BLANK_STRING,
        type: String = BLANK_STRING,
        dimension: String = BLANK_STRING
    ): Filter<LocationDomain, LocationsFilter> {
        return LocationsFilter(name, type, dimension)
    }

    override fun onSearchResult(resultList: List<SearchAble>) {
        outSource.onSearch(resultList)
    }

    override fun setOutSource(outsourceLogic: OutsourceLogic<LocationDomain, LocationsFilter>) {
        if (!this::outSource.isInitialized) outSource = outsourceLogic
    }

    companion object {
        private const val BLANK_STRING = ""
    }
}
