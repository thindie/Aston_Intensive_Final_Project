package com.example.thindie.astonrickandmorty.ui.basis

interface EventConsumer<T> {
    fun <R> onEvent(clazz: Class<R>, t: T)
}
