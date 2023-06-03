package com.example.thindie.astonrickandmorty.ui.basis.uiApi

interface OutSourced<Domain, Filter> {
    fun setOutSource(outsourceLogic: OutsourceLogic<Domain, Filter>)
}