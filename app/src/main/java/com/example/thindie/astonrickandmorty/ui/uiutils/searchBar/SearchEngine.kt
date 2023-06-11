package com.example.thindie.astonrickandmorty.ui.uiutils.searchBar

import android.util.Log
import androidx.core.widget.addTextChangedListener
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchEngineUser
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchObservable


class SearchEngine private constructor(private val engineUser: SearchEngineUser) {


    fun observeSearchCriteria() {
        when (val observable = engineUser.getSearchingEngineManager().getSearchObserveAble()) {
            is SearchObservable.WidgetHolder -> {
                observable
                    .text
                    .addTextChangedListener { changingText ->
                            engineUser.notifyStatusChanged()
                        engineUser.getSearchingConsumer()
                            .onSearchResult(filterByCriteria(changingText.toString()))
                    }

            }
            is SearchObservable.MutableStateHolder -> {
                observable.state.observe(observable.lifecycleOwner) { criteria ->
                    engineUser.notifyStatusChanged()
                    engineUser.getSearchingConsumer().onSearchResult(filterByCriteria(criteria))
                }
            }
        }
        Log.d("SERVICE_TAG", "SEARCH AT FINISH")
    }

    private fun filterByCriteria(string: String): List<SearchAble> {
        return engineUser.getSearchAbleList().filter { searchAble ->
            searchAble.getSearchingShadow().matchCriteria(string)
        }
    }


    private fun String.matchCriteria(criteria: String): Boolean {
        return lowercase().contains(criteria.lowercase()) ||
                lowercase() == criteria.lowercase() ||
                criteria.lowercase().contains(this.lowercase())
    }

    companion object {
        fun inject(engineUser: SearchEngineUser) {
            engineUser.setEngine(SearchEngine(engineUser))
        }
    }
}
