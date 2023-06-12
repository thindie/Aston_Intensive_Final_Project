package com.example.thindie.astonrickandmorty.ui.basis.uiApi

import android.util.Log
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
    private var _scrollDispatcher: ScrollDispatcher =
        ScrollDispatcher.Interrupted
    val scroll
    get() = _scrollDispatcher

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
        url: String? = null,
        idS: List<String> = emptyList(),
        filterBy: () -> Filter<Domain, Filters>
    )    {
        Log.d("SERVICE_TAG", url.toString())
        fetchSome(mapper) { provider.getAll(filter = filterBy(), url = url, idS = idS) }
    }


    suspend fun fetchConcrete(
        concretes: List<String>,
        mapper: Domain.() -> SearchAble,
        isTargetSingle: Boolean
    ) {
        if (isTargetSingle) {
            provider.getConcrete(concretes)
                .onSuccess { T ->
                    _scrollDispatcher = ScrollDispatcher.Interrupted
                    _observable.value =
                        UiState.SuccessFetchResultConcrete(T.first().mapper())
                }.onFailure { error ->
                    _observable.value = UiState.BadResult(error)
                }
        } else {
            provider.getConcrete(concretes)
                .onSuccess { T ->
                    _scrollDispatcher = ScrollDispatcher.Interrupted
                    _observable.value =
                        UiState.SuccessFetchResult(T.map(mapper))
                }.onFailure { error ->
                    _observable.value = UiState.BadResult(error)
                }
        }


    }

    fun onSearch(
        resultList: List<SearchAble>,
    ) {
        if (resultList.isEmpty()) {
            _observable.value =
                UiState.BadResultWithMessage(R.string.error_message_noting_found)
        }
        _scrollDispatcher = ScrollDispatcher.Interrupted
        //todo
        _observable.value = UiState.SuccessFetchResult(resultList)

    }


    private fun Result<List<Domain>>.onResult(mapper: (Domain) -> SearchAble) {
        onSuccess { fetched ->
            val searchAbleList = fetched.map { domain ->
                mapper(domain)
            }
            _scrollDispatcher = ScrollDispatcher.Listening
            _observable.value = UiState.SuccessFetchResult(searchAbleList)
        }.onFailure { failed ->
            _observable.value = UiState.BadResult(failed)
        }
    }

    private suspend fun fetchSome(
        mapper: (Domain) -> SearchAble,
        fetchedResult: suspend () -> Result<List<Domain>>
    )    {
        Log.d("SERVICE_TAG", "here1")
        fetchedResult().onResult(mapper)
    }


    sealed class UiState {
        data class SuccessFetchResult<UiModel : SearchAble>(
            val list: List<UiModel>
        ) : UiState()

        data class SuccessFetchResultConcrete<UiModel : SearchAble>(val fetchedUnit: UiModel) :
            UiState()

        data class BadResult(val error: Throwable) : UiState()

        object Loading : UiState()

        data class BadResultWithMessage(
            @StringRes val id: Int
        ) : UiState()
    }

    sealed class ScrollDispatcher {
        object Listening : ScrollDispatcher()
        object Interrupted : ScrollDispatcher()
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