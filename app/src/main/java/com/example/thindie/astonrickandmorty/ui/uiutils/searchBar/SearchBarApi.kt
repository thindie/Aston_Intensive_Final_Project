package com.example.thindie.astonrickandmorty.ui.uiutils.searchBar

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.textfield.TextInputEditText

interface SearchEngineManager {
    fun getSearchObserveAble(): SearchObservable
}

interface SearchEngineUser {
    fun getSearchingEngineManager(): SearchEngineManager
    fun getSearchingConsumer(): SearchEngineResultConsumer
    fun getSearchAbleList(): List<SearchAble>
    fun setEngine(engine: SearchEngine)
}

interface SearchEngineResultConsumer {
    fun onSearchResult(resultList: List<SearchAble>)
}

interface SearchAble {
    fun getSearchingShadow(): String
}

sealed class SearchObservable {
    data class WidgetHolder(val text: TextInputEditText) :
        SearchObservable()

    data class MutableStateHolder(val state: LiveData<String>, val lifecycleOwner: LifecycleOwner) :
        SearchObservable()
}