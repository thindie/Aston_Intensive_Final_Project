package com.example.thindie.astonrickandmorty.ui.uiutils.searchBar

import androidx.core.widget.addTextChangedListener
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchEngine private constructor(
    private val engineUser: SearchEngineUser,
    private val scope: CoroutineScope,
) {

    private val _isSearchTriggered: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSearchTriggered = _isSearchTriggered.asStateFlow()
    fun observeSearchCriteria() {
        var isTouched = false
        scope.launch {
            val observeAble =
                engineUser.getSearchingEngineManager().getSearchObserveAble()
            when (observeAble) {
                is SearchObserveAble.WidgetHolder -> {
                    observeAble
                        .text
                        .editText
                        ?.addTextChangedListener { changingText ->
                            if (changingText.toString().length > 1) {
                                _isSearchTriggered.tryEmit(true)
                                isTouched = true
                            } else _isSearchTriggered.tryEmit(false)
                            if (isTouched) {
                                engineUser
                                    .getSearchingConsumer()
                                    .onSearchResult(
                                        filterByCriteria(changingText.toString())
                                    )
                            }
                        }
                }
                is SearchObserveAble.MutableStateHolder -> {
                    observeAble
                        .state
                        .observe(observeAble.lifecycleOwner) { criteria ->
                            engineUser
                                .getSearchingConsumer()
                                .onSearchResult(
                                    filterByCriteria(criteria)
                                )
                        }
                }
            }
        }
    }

    private fun filterByCriteria(string: String): List<OperationAble> {
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
        fun inject(engineUser: SearchEngineUser, coroutineScope: CoroutineScope) {
            engineUser.setEngine(SearchEngine(engineUser, coroutineScope))
        }
    }
}
