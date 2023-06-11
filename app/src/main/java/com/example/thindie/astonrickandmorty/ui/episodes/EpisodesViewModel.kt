package com.example.thindie.astonrickandmorty.ui.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeProvider
import com.example.thindie.astonrickandmorty.domain.filtering.EpisodeFilter
import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import com.example.thindie.astonrickandmorty.ui.basis.mappers.mapEpisodesDomain
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.EventMediator
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.RecyclerViewAdapter
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.RecyclerViewAdapterManager
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

class EpisodesViewModel @Inject constructor(provider: EpisodeProvider) : ViewModel(),
    SearchEngineResultConsumer,
    OutSourced<EpisodeDomain, EpisodeFilter>,
    RecyclerViewAdapterManager<EpisodesUiModel, UiHolder, Scroll> {

    override val adapter: RecyclerViewAdapter<EpisodesUiModel, UiHolder>
        get() = _adapterHolder
    private lateinit var _adapterHolder: RecyclerViewAdapter<EpisodesUiModel, UiHolder>
    private lateinit var outSource: OutsourceLogic<EpisodeDomain, EpisodeFilter>

    init {
        OutsourceLogic.inject(this, provider)
    }

    val viewState: LiveData<OutsourceLogic.UiState>
        get() = outSource.observable


    fun onClickedNavigationButton() {
        viewModelScope.launch {
            outSource.fetchAll(mapEpisodesDomain) {
                onApplyFilter()
            }
        }
    }

    fun clickConcrete(id: Int) {
        viewModelScope.launch {
            outSource.fetchConcrete(id, mapEpisodesDomain)
        }
    }

    fun onApplyFilter(
        name: String = BLANK_STRING,
        episode: String = BLANK_STRING
    ): Filter<EpisodeDomain, EpisodeFilter> {
        return EpisodeFilter(name, episode)
    }

    override fun onSearchResult(resultList: List<SearchAble>) {
        outSource.onSearch(resultList)
    }


    override fun setOutSource(outsourceLogic: OutsourceLogic<EpisodeDomain, EpisodeFilter>) {
        if (!this::outSource.isInitialized) outSource = outsourceLogic
    }

    override fun setAdapter(adapter: RecyclerViewAdapter<EpisodesUiModel, UiHolder>) {
        if (!this::_adapterHolder.isInitialized) _adapterHolder = adapter
    }


    override fun observe(eventStateListener: EventMediator<Scroll>) {
        viewModelScope.launch {
            eventStateListener
                .eventFlow
                .onEach { scroll -> if(eventStateListener.isActive()) {
                    outSource.onScrollEvent(scroll) { onClickedNavigationButton() }
                } }
                .launchIn(this)
        }
    }

    companion object {
        private const val BLANK_STRING = ""
    }


}