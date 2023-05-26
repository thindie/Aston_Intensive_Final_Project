package com.example.thindie.astonrickandmorty.ui.uiutils.searchBar

import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble

interface SearchEngineResultConsumer {
    fun onSearchResult(resultList: List<OperationAble>)
    fun shareActualList(): List<OperationAble>
}
