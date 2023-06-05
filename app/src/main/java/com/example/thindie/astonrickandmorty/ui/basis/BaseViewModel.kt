package com.example.thindie.astonrickandmorty.ui.basis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thindie.astonrickandmorty.domain.BaseProvider
import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble
import kotlinx.coroutines.launch

abstract class BaseViewModel<Domain, Filters>(private val provider: BaseProvider<Domain, Filters>) :
    ViewModel() {

    abstract fun applyFilter(): Filter<Domain, Filters>
    abstract fun SearchAble.transform(): Domain


    private val _observable: MutableLiveData<UiState> = MutableLiveData()

    val observable: LiveData<UiState>
        get() = _observable

    fun fetchAll() {
        fetchSome { provider.getAll(filter = applyFilter()) }
    }


    fun fetchConcrete(id: Int) {
        viewModelScope.launch {
            provider.getConcrete(id)
                .onSuccess { T ->
                    _observable.value = UiState.SuccessFetchResultConcrete(T)
                }.onFailure { error ->
                    _observable.value = UiState.BadResult(error)
                }
        }
    }

    protected fun onSearch(resultList: List<SearchAble>) {
        val list = resultList
            .map { searchAble ->
                searchAble.transform()
            }
        _observable.value = UiState.SuccessFetchResult(list)
    }


    private fun Result<List<Domain>>.onFetch() {
        onSuccess { fetched ->
            _observable.value = UiState.SuccessFetchResult(fetched)
        }.onFailure { failed ->
            _observable.value = UiState.BadResult(failed)
        }
    }

    private fun fetchSome(foo: suspend () -> Result<List<Domain>>) {
        viewModelScope.launch {
            foo().onFetch()
        }
    }


    sealed class UiState {
        data class SuccessFetchResult<Domain>(
            val list: List<Domain>
        ) : UiState()

        data class SuccessFetchResultConcrete<Domain>(val t: Domain) : UiState()

        data class BadResult(val error: Throwable) : UiState()
    }
}