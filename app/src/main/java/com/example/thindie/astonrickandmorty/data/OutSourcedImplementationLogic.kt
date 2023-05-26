package com.example.thindie.astonrickandmorty.data

interface OutSourcedImplementationLogic<Domain, Remote, Local> {
    fun setOutSource(logic: OutSourceLogic<Domain, Remote, Local>)
}
