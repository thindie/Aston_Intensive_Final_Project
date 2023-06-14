package com.example.thindie.astonrickandmorty.ui.basis

import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchEngineResultConsumer

abstract class BaseConcreteFragment : BaseFragment() {

    override fun getSearchingConsumer(): SearchEngineResultConsumer {
        return object : SearchEngineResultConsumer {
            override fun onSearchResult(resultList: List<SearchAble>) {
                error("stub!")
            }
        }
    }

    override fun getSearchAbleList(): List<SearchAble> {
        return emptyList()
    }

    override fun actAsAChildFragment() {
        error("stub")
    }

}