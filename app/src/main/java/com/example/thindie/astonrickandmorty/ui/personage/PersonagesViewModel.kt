package com.example.thindie.astonrickandmorty.ui.personage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import com.example.thindie.astonrickandmorty.domain.filtering.PersonageFilter
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain
import com.example.thindie.astonrickandmorty.domain.personages.PersonageProvider
import com.example.thindie.astonrickandmorty.ui.basis.mappers.mapPersonageDomain
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.EventMediator
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.RecyclerViewAdapter
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.RecyclerViewAdapterManager
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.RecyclerViewAdapterMediatorScroll
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.Scroll
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.UiHolder
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutSourced
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutsourceLogic
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchEngineResultConsumer
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PersonagesViewModel @Inject constructor(personageProvider: PersonageProvider) : ViewModel(),
    SearchEngineResultConsumer, OutSourced<PersonageDomain, PersonageFilter>,
    RecyclerViewAdapterManager<PersonagesUiModel, UiHolder, Scroll> {

    private lateinit var outSource: OutsourceLogic<PersonageDomain, PersonageFilter>

    init {
        OutsourceLogic.inject(this, personageProvider)
    }

    val viewState: LiveData<OutsourceLogic.UiState>
        get() = outSource.observable


    fun onClickedNavigationButton() {
        viewModelScope.launch {
            outSource.fetchAll(mapPersonageDomain) {
                onApplyFilter()
            }
        }
    }

    fun onReactScrollDown() {
        if (outSource.scroll is OutsourceLogic.ScrollDispatcher.Listening)
            viewModelScope.launch {

                outSource.fetchAll(
                    mapPersonageDomain,
                    url = "https://rickandmortyapi.com/api/character/?page=2"
                ) {
                    onApplyFilter()
                }
            }

    }

    fun onReactScrollUp() {
        viewModelScope.launch {
            outSource.fetchAll(mapPersonageDomain, url = adapter.currentList.last().pool.prev) {
                onApplyFilter()
            }
        }
    }

    fun onClickConcrete(id: Int, isTargetSingle: Boolean = true) {
        viewModelScope.launch {
            outSource.fetchConcrete(listOf(id.toString()), mapPersonageDomain, isTargetSingle)
        }
    }

    fun onConcreteScreenObtainList(links: List<String>, isTargetSingle: Boolean = false) {
        viewModelScope.launch {
            outSource.fetchConcrete(links, mapPersonageDomain, isTargetSingle)
        }
    }

    fun onApplyFilter(
        name: String = BLANK_STRING,
        status: PersonageFilter.Companion.Status = PersonageFilter.Companion.Status.UNSPECIFIED,
        species: String = BLANK_STRING,
        type: String = BLANK_STRING,
        gender: PersonageFilter.Companion.Gender = PersonageFilter.Companion.Gender.UNSPECIFIED
    ): Filter<PersonageDomain, PersonageFilter> {
        return PersonageFilter()
    }


    override fun onSearchResult(resultList: List<SearchAble>) {
        outSource.onSearch(resultList)
    }

    override fun setOutSource(outsourceLogic: OutsourceLogic<PersonageDomain, PersonageFilter>) {
        if (!this::outSource.isInitialized) outSource = outsourceLogic
    }

    companion object {
        private const val BLANK_STRING = ""
    }

    private lateinit var _adapter: RecyclerViewAdapterMediatorScroll<PersonagesUiModel, UiHolder>
    override val adapter: RecyclerViewAdapterMediatorScroll<PersonagesUiModel, UiHolder>
        get() = _adapter

    override fun observe(eventStateListener: EventMediator<Scroll>) {
        eventStateListener
            .event = {
                scroll ->  onReactScrollDown()
        }

    }

    override fun <Adapter : RecyclerViewAdapter<PersonagesUiModel, UiHolder>> setAdapter(adapter: Adapter) {
        if (!this::_adapter.isInitialized) {

            _adapter = adapter as RecyclerViewAdapterMediatorScroll<PersonagesUiModel, UiHolder>
        }
    }
}