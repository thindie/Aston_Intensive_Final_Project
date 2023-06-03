package com.example.thindie.astonrickandmorty.ui.personage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import com.example.thindie.astonrickandmorty.domain.filtering.PersonageFilter
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain
import com.example.thindie.astonrickandmorty.domain.personages.PersonageProvider
import com.example.thindie.astonrickandmorty.ui.basis.mappers.mapPersonageDomain
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutSourced
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutsourceLogic
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer
import javax.inject.Inject
import kotlinx.coroutines.launch

class PersonagesViewModel @Inject constructor(personageProvider: PersonageProvider) : ViewModel(),
    SearchEngineResultConsumer, OutSourced<PersonageDomain, PersonageFilter> {

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

    fun clickConcrete(id: Int) {
        viewModelScope.launch {
            outSource.fetchConcrete(id, mapPersonageDomain)
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
}