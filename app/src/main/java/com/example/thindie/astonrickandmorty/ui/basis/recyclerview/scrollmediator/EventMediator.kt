package com.example.thindie.astonrickandmorty.ui.basis.recyclerview.scrollmediator

interface EventMediator<T> {
    var event: ((T) -> Unit)?
    fun onEvent(t: T)

}
