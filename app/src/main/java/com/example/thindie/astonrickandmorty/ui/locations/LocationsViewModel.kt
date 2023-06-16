package com.example.thindie.astonrickandmorty.ui.locations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import com.example.thindie.astonrickandmorty.domain.filtering.LocationsFilter
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.domain.locations.LocationProvider
import com.example.thindie.astonrickandmorty.ui.basis.mappers.mapLocationDomain
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.EventMediator
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.RecyclerViewAdapter
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.RecyclerViewAdapterFragment
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.RecyclerViewAdapterManager
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.RecyclerViewAdapterMediatorScroll
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.Scroll
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.UiHolder
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutSourced
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutsourceLogic
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesFragment
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchEngineResultConsumer
import javax.inject.Inject
import kotlinx.coroutines.launch

class LocationsViewModel @Inject constructor(private val provider: LocationProvider) : ViewModel(),
    SearchEngineResultConsumer, OutSourced<LocationDomain, LocationsFilter>,
    RecyclerViewAdapterManager<LocationsUiModel, UiHolder, PersonagesFragment, Scroll> {

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

    private fun onReactScrollDown() {
        viewModelScope.launch {
            outSource.fetchAll(mapLocationDomain, url = adapter.currentList.last().pool.next) {
                Log.d("SERVICE_TAG", adapter.currentList.last().pool.next)
                onApplyFilter()
            }
        }
    }

    private fun onReactScrollUp() {
        viewModelScope.launch {
            outSource.fetchAll(mapLocationDomain, url = adapter.currentList.last().pool.prev) {
                onApplyFilter()
            }
        }
    }

    fun onClickConcrete(id: Int) {
        viewModelScope.launch {
            outSource.fetchConcrete(id, mapLocationDomain )
        }
    }

    fun onConcreteScreenObtainList(links: List<String>) {
        viewModelScope.launch {
            outSource.fetchPool(links, mapLocationDomain )
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

    private lateinit var _adapter: RecyclerViewAdapterMediatorScroll<LocationsUiModel, UiHolder>

    override val adapter: RecyclerViewAdapterMediatorScroll<LocationsUiModel, UiHolder>
        get() = _adapter



    override fun observe(eventStateListener: EventMediator<Scroll>) {
        eventStateListener
            .event = { scrollEvent ->
            when (scrollEvent) {
                Scroll.TOP -> {
                    onReactScrollUp()
                }
                Scroll.STILL -> {}
                Scroll.BOTTOM -> {
                    onReactScrollDown()
                }
            }
        }

    }


    override fun <Adapter : RecyclerViewAdapterFragment<LocationsUiModel, UiHolder, PersonagesFragment>> setAdapter(
        adapter: Adapter
    ) {
        if (!this::_adapter.isInitialized) {

            _adapter = adapter
        }
    }
}
