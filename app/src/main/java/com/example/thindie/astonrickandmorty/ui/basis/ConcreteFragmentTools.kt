package com.example.thindie.astonrickandmorty.ui.basis

import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble

interface ConcreteFragmentTools {
    fun initialiseParent(searchAble: SearchAble)
    fun initialiseChild(isChild: Boolean)
}