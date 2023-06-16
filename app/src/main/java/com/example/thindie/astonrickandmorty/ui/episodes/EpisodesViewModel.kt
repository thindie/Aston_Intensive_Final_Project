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

class EpisodesViewModel @Inject constructor(provider: EpisodeProvider) : ViewModel(),
    SearchEngineResultConsumer,
    OutSourced<EpisodeDomain, EpisodeFilter>,
    RecyclerViewAdapterManager<EpisodesUiModel, UiHolder, PersonagesFragment, Scroll,> {

    override val adapter: RecyclerViewAdapterFragment<EpisodesUiModel, UiHolder, PersonagesFragment>
        get() = _adapterHolder
    private lateinit var _adapterHolder: RecyclerViewAdapterFragment<EpisodesUiModel, UiHolder, PersonagesFragment>
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

    private fun onReactScrollDown(){
        viewModelScope.launch {
            outSource.fetchAll(mapEpisodesDomain, url = adapter.currentList.last().pool.next){
                onApplyFilter()
            }
        }
    }

    private fun onReactScrollUp(){
        viewModelScope.launch {
            outSource.fetchAll(mapEpisodesDomain, url = adapter.currentList.last().pool.prev){
                onApplyFilter()
            }
        }
    }

    fun onClickConcrete(id: Int) {
        viewModelScope.launch {
            outSource.fetchConcrete(id, mapEpisodesDomain)
        }
    }

    fun onConcreteScreenObtainList(links: List<String>) {
        viewModelScope.launch {
            outSource.fetchPool(links, mapEpisodesDomain)
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


    override fun <Adapter : RecyclerViewAdapterFragment<EpisodesUiModel, UiHolder, PersonagesFragment>> setAdapter(adapter: Adapter) {
        if (!this::_adapterHolder.isInitialized) {
            _adapterHolder =  adapter
        }
    }

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

    companion object {
        private const val BLANK_STRING = ""
    }


}