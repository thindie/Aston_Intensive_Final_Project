package com.example.thindie.astonrickandmorty.ui.basis.uiApi

import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble

interface ConcreteFragmentTools {
    fun initialiseParent(searchAble: SearchAble)
    fun initialiseChild(arguments: Any)
}