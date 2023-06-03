package com.example.thindie.astonrickandmorty.ui.uiutils.searchBar

import androidx.core.widget.addTextChangedListener


class SearchEngine private constructor(private val engineUser: SearchEngineUser) {


    fun observeSearchCriteria() {
        when (val observable = engineUser.getSearchingEngineManager().getSearchObserveAble()) {
            is SearchObservable.WidgetHolder -> {
                observable
                    .text
                    .addTextChangedListener { changingText ->
                        engineUser.getSearchingConsumer()
                            .onSearchResult(filterByCriteria(changingText.toString()))
                    }
            }
            is SearchObservable.MutableStateHolder -> {
                observable.state.observe(observable.lifecycleOwner) { criteria ->
                    engineUser.getSearchingConsumer().onSearchResult(filterByCriteria(criteria))
                }
            }
        }
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
