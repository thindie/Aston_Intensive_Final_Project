package com.example.thindie.astonrickandmorty.ui.uiutils.searchBar

import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble

interface SearchEngineUser {
    fun getSearchingEngineManager(): SearchEngineManager
    fun getSearchingConsumer(): SearchEngineResultConsumer
    fun getSearchAbleList(): List<OperationAble>
    fun setEngine(engine: SearchEngine)
}
