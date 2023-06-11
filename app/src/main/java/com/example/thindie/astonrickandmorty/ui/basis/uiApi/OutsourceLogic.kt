package com.example.thindie.astonrickandmorty.ui.basis.uiApi

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.domain.BaseProvider
import com.example.thindie.astonrickandmorty.domain.filtering.Filter
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.Scroll
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble

class OutsourceLogic<Domain, Filters>(private val provider: BaseProvider<Domain, Filters>) {


    private val _observable: MutableLiveData<UiState> = MutableLiveData()

    val observable: LiveData<UiState>
        get() = _observable


    fun onScrollEvent(scroll: Scroll, action: () -> Unit) {
        when (scroll) {
            Scroll.STILL -> {
                /* ignore */
            }
            Scroll.BOTTOM -> {
                _observable.value = UiState.Loading
                action()
            }
            Scroll.TOP -> {
                _observable.value = UiState.Loading
                action()
            }
        }
    }

    suspend fun fetchAll(
        mapper: (Domain) -> SearchAble,
        filterBy: () -> Filter<Domain, Filters>
    ) {
        fetchSome(mapper) { provider.getAll(filter = filterBy()) }
    }


    suspend fun fetchConcrete(id: Int, mapper: Domain.() -> SearchAble) {

        provider.getConcrete(id)
            .onSuccess { T ->
                _observable.value =
                    UiState.SuccessFetchResultConcrete(T.mapper())
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


    private fun Result<List<Domain>>.onFetch(foo: (Domain) -> SearchAble) {
        onSuccess { fetched ->
            val toMap = fetched.map {
                foo(it)
            }
            _observable.value = UiState.SuccessFetchResult(toMap)
        }.onFailure { failed ->
            _observable.value = UiState.BadResult(failed)
        }
    }

    private suspend fun fetchSome(
        bar: (Domain) -> SearchAble,
        foo: suspend () -> Result<List<Domain>>
    ) {

        foo().onFetch(bar)

    }


    sealed class UiState {
        data class SuccessFetchResult<UiModel : SearchAble>(
            val list: List<UiModel>
        ) : UiState()

        data class SuccessFetchResultConcrete<UiModel : SearchAble>(val t: UiModel) :
            UiState()

        data class BadResult(val error: Throwable) : UiState()

        object Loading : UiState()

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