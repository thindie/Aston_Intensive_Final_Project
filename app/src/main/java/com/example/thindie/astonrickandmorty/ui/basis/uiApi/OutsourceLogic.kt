package com.example.thindie.astonrickandmorty.ui.basis.uiApi

import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.domain.BaseProvider
import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble

class OutsourceLogic<Domain, Filters>(private val provider: BaseProvider<Domain, Filters>) {

    private val _observable: MutableLiveData<UiState> = MutableLiveData()
    val observable: LiveData<UiState>
        get() = _observable


    suspend fun fetchAll(
        mapper: (Domain) -> SearchAble,
        url: String? = null,
        idS: List<String> = emptyList(),
        filterBy: () -> Filter<Domain, Filters>
    ) {
        _observable.value = UiState.Loading
        fetchSome(mapper) { provider.getAll(filter = filterBy(), url = url, idS = idS) }
    }


    suspend fun fetchPool(
        concretes: List<String>,
        mapper: Domain.() -> SearchAble,
    ) {
        _observable.value = UiState.Loading
        provider.getPoolOf(concretes)
            .onSuccess { resultPool ->
                _observable.value =
                    UiState.SuccessFetchResult(resultPool.map { mapper(it) })
            }.onFailure { error ->
                _observable.value = UiState.BadResult(error)
            }
    }

    suspend fun fetchConcrete(
        id: Int,
        mapper: Domain.() -> SearchAble,
    ) {
        provider.getConcrete(id)
            .onSuccess { concreteResult ->
                _observable.value =
                    UiState.SuccessFetchResultConcrete(mapper(concreteResult))
            }.onFailure { error ->
                _observable.value = UiState.BadResult(error)
            }
    }

    fun onSearch(
        resultList: List<SearchAble>,
    ) {
        if (resultList.isEmpty()) {
            _observable.value =
                UiState.BadResultWithMessage(R.string.error_message_noting_found)
        }

        _observable.value = UiState.SuccessFetchResult(resultList)

    }


    private fun Result<List<Domain>>.onResult(mapper: (Domain) -> SearchAble) {
        onSuccess { fetched ->
            val searchAbleList = fetched.map { domain ->
                mapper(domain)
            }

            _observable.value = UiState.SuccessFetchResult(searchAbleList)
        }.onFailure { failed ->
            _observable.value = UiState.BadResult(failed)
        }
    }

    private suspend fun fetchSome(
        mapper: (Domain) -> SearchAble,
        fetchedResult: suspend () -> Result<List<Domain>>
    ) {

        fetchedResult().onResult(mapper)
    }


    sealed class UiState {

        fun hide(view: View) {
            view.visibility = View.GONE
        }

        data class SuccessFetchResult<UiModel : SearchAble>(
            val list: List<UiModel>
        ) : UiState()

        data class SuccessFetchResultConcrete<UiModel : SearchAble>(val fetchedUnit: UiModel) :
            UiState()

        data class BadResult(val error: Throwable) : UiState()

        object Loading : UiState() {
            fun show(view: View) {
                view.visibility = View.VISIBLE
            }
        }

        data class BadResultWithMessage(
            @StringRes val id: Int
        ) : UiState()
    }


    companion object {
        fun <Domain, Filter> inject(
            outSourced: OutSourced<Domain, Filter>,
            provider: BaseProvider<Domain, Filter>
        ) {
            outSourced.setOutSource(OutsourceLogic(provider))
        }

    }
}